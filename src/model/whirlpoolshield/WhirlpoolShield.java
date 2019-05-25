package model.whirlpoolshield;

import controller.Main;
import model.GameFigure;
import model.lobstermon.lobsterMon;
import model.turtlehero.turtleHero;
import model.waterbeam.WaterBeamAnimExploding;
import model.waterbeam.WaterBeamAnimShooting;
import model.waterbeam.WaterBeamAnimStrategy;

import java.awt.*;
import java.awt.geom.Point2D;

public class WhirlpoolShield extends GameFigure {

    public static final int UNIT_MOVE = 0;
    public static final int INIT_SHIELD_SIZE = 20;
    public static final int MAX_SHIELD_SIZE = 40;


    int size = INIT_SHIELD_SIZE;
    Point2D.Float target; // where mouse was pressed, get target location and draw the path towards it
    Point2D.Float longTarget;


    Color color; // color of the bubble
    double rad;


    public static final int STATE_DEFENSING = 0;
    public static final int STATE_DISAPPEARING = 1;
    public static final int STATE_DONE = 2;

    int state;
    int explodeCount =0;

    WhirlpoolShieldAnimStrategy animStrategy;

    Color blueColor1 = new Color (193,215,235); // 155-255
    Color blueColor2 = new Color (178,215,235); // 155-255
    Color blueColor3 = new Color (163,215,235); // the smallest one -100
    Color blueColor4 = new Color (148,215,235); // the smallest one -100
    Color blueColor5 = new Color (133,215,235); // the smallest one -100
    Color whiteColor = new Color(233, 230, 224);

    int colorCount = 0;
    int colorSwitchCount = 0;
    int colorSwitchIndex = 0;

    public WhirlpoolShield(int tx, int ty) { // x->tx, y->ty, target, that is, mouse press location

        turtleHero turtleHero = (model.turtlehero.turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

        //initial location of the bubble <- the end of the barrel
        super.location.x = turtleHero.location.x -14; // almost the nose of turtle
        super.location.y = turtleHero.location.y +1; // almost the nose of turtle

        target = new Point2D.Float(super.location.x,super.location.y-2000); // mousepress location as target

        color = new Color(168,255,255); // initial color

        animStrategy = new WhirlpoolShieldAnimDefensing(this);

        friendINDEX = 6;//whirlpoolshield
        chargeCount = 0;

    }

    @Override
    public void render(Graphics2D g2) {

        //always follow the turtle hero, because super.location set as turtle hero location
        turtleHero turtleHero = (model.turtlehero.turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
        super.location.x = turtleHero.location.x -14; // almost the nose of turtle
        super.location.y = turtleHero.location.y +1-15; // almost the nose of turtle


        g2.setColor(color);
        g2.setStroke(new BasicStroke(1)); // change the thickness back to 1
//        g2.fillOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);

//        if (chargeCount <= 10){
//            g2.fillOval((int) super.location.x - 20 / 2, (int) super.location.y - 20 / 2, 20, 20);
//        }
//        else if (chargeCount <= 20){
//            g2.fillOval((int) super.location.x - 40 / 2, (int) super.location.y - 40 / 2, 40, 40);
//        }
//        else if (chargeCount <= 30){
//            g2.fillOval((int) super.location.x - 60 / 2, (int) super.location.y - 60 / 2, 60, 60);
//        }
//        else {
//            g2.fillOval((int) super.location.x - 80 / 2, (int) super.location.y - 80 / 2, 80, 80);
//        }



//        if (chargeCount <= 20) { // charging
//            //bottom oval
//            switch(colorSwitchIndex){
//                case 0: g2.setColor(blueColor1); break;
//                case 1: g2.setColor(blueColor2); break;
//                case 2: g2.setColor(blueColor3); break;
//                case 3: g2.setColor(blueColor4); break;
//                case 4: g2.setColor(blueColor5); break;
//            }
//            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*4-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*4-(chargeCount*4/3-2)*4-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*5, 12-6+chargeCount/10*3);
//            g2.setColor(whiteColor);
//            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*4-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*4-(chargeCount*4/3-2)*4-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*5, 12-6+chargeCount/10*3);
//
//            //2nd oval
//            switch(colorSwitchIndex){
//                case 0: g2.setColor(blueColor2); break;
//                case 1: g2.setColor(blueColor3); break;
//                case 2: g2.setColor(blueColor4); break;
//                case 3: g2.setColor(blueColor5); break;
//                case 4: g2.setColor(blueColor1); break;
//            }
//            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*3-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*3-(chargeCount*4/3-2)*3-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*4, 12-6+chargeCount/5);
//            g2.setColor(whiteColor);
//            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*3-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*3-(chargeCount*4/3-2)*3-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*4, 12-6+chargeCount/5);
//
//            //3rd oval
//            switch(colorSwitchIndex){
//                case 0: g2.setColor(blueColor3); break;
//                case 1: g2.setColor(blueColor4); break;
//                case 2: g2.setColor(blueColor5); break;
//                case 3: g2.setColor(blueColor1); break;
//                case 4: g2.setColor(blueColor2); break;
//            }
//            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*2-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*2-(chargeCount*4/3-2)*2-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*3, 12-6+chargeCount/10);
//            g2.setColor(whiteColor);
//            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (60+chargeCount*2-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3*2-(chargeCount*4/3-2)*2-chargeCount/10-(20-chargeCount)/3,
//                    40+chargeCount*3, 12-6+chargeCount/10);
//
//
//            //4th oval
//            switch(colorSwitchIndex){
//                case 0: g2.setColor(blueColor4); break;
//                case 1: g2.setColor(blueColor5); break;
//                case 2: g2.setColor(blueColor1); break;
//                case 3: g2.setColor(blueColor2); break;
//                case 4: g2.setColor(blueColor3); break;
//            }
//            g2.fillOval((int) super.location.x - 80 / 2 +22-22- (60+chargeCount-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2 + chargeCount*4/3-(chargeCount*4/3-2)-chargeCount/20-(20-chargeCount)/3,
//                    40+chargeCount*2, 12-6);
//            g2.setColor(whiteColor);
//            g2.drawOval((int) super.location.x - 80 / 2 +22-22- (60+chargeCount-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2+ chargeCount*4/3-(chargeCount*4/3-2)-chargeCount/20-(20-chargeCount)/3,
//                    40+chargeCount*2, 12-6);
//
//            //top oval
//            switch(colorSwitchIndex){
//                case 0: g2.setColor(blueColor5); break;
//                case 1: g2.setColor(blueColor1); break;
//                case 2: g2.setColor(blueColor2); break;
//                case 3: g2.setColor(blueColor3); break;
//                case 4: g2.setColor(blueColor4); break;
//            }
//            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (60-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2-(20-chargeCount)/3,
//                    40+chargeCount, 12-6);
//            g2.setColor(whiteColor);
//            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (60-80)/2 +(20-chargeCount)/3+1,
//                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2 -(20-chargeCount)/3,
//                    40+chargeCount, 12-6);
//
//        }
//        else { // maximum

            //bottom oval
            switch(colorSwitchIndex){
                case 0: g2.setColor(blueColor1); break;
                case 1: g2.setColor(blueColor2); break;
                case 2: g2.setColor(blueColor3); break;
                case 3: g2.setColor(blueColor4); break;
                case 4: g2.setColor(blueColor5); break;
            }
            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (140-80)/2,
                    (int) super.location.y+30-45+10,
                    140, 12);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (140-80)/2,
                    (int) super.location.y+30-45+10,
                    140, 12);

            //2nd oval
            switch(colorSwitchIndex){
                case 0: g2.setColor(blueColor2); break;
                case 1: g2.setColor(blueColor3); break;
                case 2: g2.setColor(blueColor4); break;
                case 3: g2.setColor(blueColor5); break;
                case 4: g2.setColor(blueColor1); break;
            }
            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (120-80)/2,
                    (int) super.location.y+30-27-45+24+10,
                    120, 12-2);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (120-80)/2,
                    (int) super.location.y+30-27-45+24+10,
                    120, 12-2);

            //3rd oval
            switch(colorSwitchIndex){
                case 0: g2.setColor(blueColor3); break;
                case 1: g2.setColor(blueColor4); break;
                case 2: g2.setColor(blueColor5); break;
                case 3: g2.setColor(blueColor1); break;
                case 4: g2.setColor(blueColor2); break;
            }
            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (100-80)/2,
                    (int) super.location.y+30-27-26-45+24*2+10,
                    100, 12-4);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (100-80)/2,
                    (int) super.location.y+30-27-26-45+24*2+10,
                    100, 12-4);


            //4th oval
            switch(colorSwitchIndex){
                case 0: g2.setColor(blueColor4); break;
                case 1: g2.setColor(blueColor5); break;
                case 2: g2.setColor(blueColor1); break;
                case 3: g2.setColor(blueColor2); break;
                case 4: g2.setColor(blueColor3); break;
            }
            g2.fillOval((int) super.location.x - 80 / 2 +22-22,
                    (int) super.location.y+30-27-26-27-45+24*3+10+1,
                    80, 12-6);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22-22,
                    (int) super.location.y+30-27-26-27-45+24*3+10+1,
                    80, 12-6);

            //top oval
            switch(colorSwitchIndex){
                case 0: g2.setColor(blueColor5); break;
                case 1: g2.setColor(blueColor1); break;
                case 2: g2.setColor(blueColor2); break;
                case 3: g2.setColor(blueColor3); break;
                case 4: g2.setColor(blueColor4); break;
            }
            g2.fillOval((int) super.location.x - 80 / 2 +22-22 - (60-80)/2,
                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2,
                    60, 12-6);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22-22 - (60-80)/2,
                    (int) super.location.y+30-27-26-27-26+24*4-45+10 +2,
                    60, 12-6);



//        }

        chargeCount++;
        colorCount++;
//        if (colorCount % 3 == 0) {
            colorSwitchCount++;
            if(colorSwitchCount%2==0) {
//                if(colorSwitchOn == true) colorSwitchOn = false; // if two color like tick tock
//                else colorSwitchOn = true;
                if (colorSwitchIndex <4){ // changing from 0, 1, 2, 3 to 4 (5colors)
                    colorSwitchIndex++;
                } else colorSwitchIndex = 0; // if =4, then set back to 0
            }
//        }
    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate();

//            if (target.distance(location) <= 8.0) {
//                //explosion effect
//                if (size <= MAX_BUBBLE_SIZE) size += 5;
//
//                if (size == MAX_BUBBLE_SIZE || size == MAX_BUBBLE_SIZE + 1) { // because ++
//                    while (size > 0) size -= 15;
//                }
//
//            } else {
//
//
//                double delta_x = target.x - location.x;
//                double delta_y = target.y - location.y;
//
//                double direction = Math.atan2(delta_y, delta_x);
//
//                super.location.x += UNIT_MOVE * Math.cos(direction); // opposite(delta x)/hypotenuse
//                super.location.y += UNIT_MOVE * Math.sin(direction); // adjacent(delta y)/hypotenuse
//            }

    }

    private void updateState() {
//        if (state == STATE_EXPANDING) {
//            if (chargeCount >=25) {
//                state = STATE_DEFENSING;
//            }
//            if (hitCount > 0 || target.distance(location) <= 10.0) {
//                state = STATE_DISAPPEARING;
//                color = Color.WHITE;
//                animStrategy = new WhirlpoolShieldAnimDisappearing(this);
//
//            }
//        }
//        else
        if (state == STATE_DEFENSING) { // defensing mode
//            if (lobsterMon.currentLobsterMon != null) { // existing lobsterMon and chase mode
//                if (hitCount == 0 && lobsterMon.currentLobsterMon.location.distance(location) <= 400.0) {
//                    if (location.y>-10){
////                        state = STATE_CHASING;
////                        animStrategy = new WaterBeamAnimChasing(this);
////                    color = new Color(value1,value2,value3);
////                    System.out.println("testing1");
//                    }
//                }
//            }

//            if (hitCount > 0 || target.distance(location) <= 10.0) {
//                state = STATE_DISAPPEARING;
//                color = Color.WHITE;
//                animStrategy = new WhirlpoolShieldAnimDisappearing(this);
//
//            }
            if (chargeCount>=40) {
                state = STATE_DISAPPEARING;
            }
        }
//        else if (state == STATE_CHASING) {
//            if (hitCount > 0 || target.distance(location) <= 10.0) {
//                state = STATE_DISAPPEARING;
//                color = Color.WHITE;
//                animStrategy = new WaterBeamAnimExploding(this);
////                System.out.println(hitCount);
//            }

//        }

        else if (state == STATE_DISAPPEARING) {
//            if (size >= MAX_SHIELD_SIZE) {
            if (chargeCount>=50) {
                state = STATE_DONE;
//                System.out.println("testing4");
            }
        }
        else if (state == STATE_DONE) {
            super.done = true;
        }

    }

    @Override
    public int getCollisionRadius() {
        return (size+5)/2*2; // = 25
    }
}
