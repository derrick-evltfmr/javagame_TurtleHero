package model.waterbeam;

import controller.Main;
import model.GameFigure;
import model.lobstermon.lobsterMon;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class WaterBeam extends GameFigure {

    public static final int UNIT_MOVE = 0;
    public static final int INIT_BEAM_SIZE = 20;
    public static final int MAX_BEAM_SIZE = 40;

    int size = INIT_BEAM_SIZE;
    Point2D.Float target; // where mouse was pressed, get target location and draw the path towards it
    Point2D.Float longTarget;


    Color color; // color of the bubble
    double rad;


    public static final int STATE_CHARGING = 0;
    public static final int STATE_SHOOTING = 1;
    public static final int STATE_EXPLODING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_CHASING =5;

    int state;
    int explodeCount =0;

    WaterBeamAnimStrategy animStrategy;

    Color lightColor = new Color (173,213,233); // 155-255
    Color deepColor = new Color (123,213,233); // the smallest one -100
    Color whiteColor = new Color(233, 230, 224);

    public WaterBeam(int tx, int ty) { // x->tx, y->ty, target, that is, mouse press location

        turtleHero turtleHero = (model.turtlehero.turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

        //initial location of the bubble <- the end of the barrel
        super.location.x = turtleHero.location.x -14; // almost the nose of turtle
        super.location.y = turtleHero.location.y +1-10; // almost the nose of turtle

        target = new Point2D.Float(super.location.x,super.location.y-2000); // mousepress location as target

        color = new Color(168,255,255); // initial color

        animStrategy = new WaterBeamAnimShooting(this);

        friendINDEX = 4;
        chargeCount = 0;

    }

    @Override
    public void render(Graphics2D g2) {
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



        if (chargeCount <= 20) { // charging
            //bottom out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-45,
                    36, 18);

            //bottom in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-45,
                    26, 13);

            //rectangle bar
            g2.setColor(deepColor);
            g2.fillRect((int) super.location.x - 80 / 2 +27, (int) super.location.y+32-chargeCount*4-45,
                    26,chargeCount*4);
            g2.setColor(whiteColor);
            g2.drawLine((int) super.location.x - 80 / 2 +27,(int) super.location.y+32-chargeCount*4-45,
                    (int) super.location.x - 80 / 2 +27,(int) super.location.y+32-chargeCount*4+chargeCount*4-45);
            g2.drawLine((int) super.location.x - 80 / 2 +27+26,(int) super.location.y+32-chargeCount*4-45,
                    (int) super.location.x - 80 / 2 +27+26,(int) super.location.y+32-chargeCount*4+chargeCount*4-45);

            //2nd out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-45,
                    36, 18);

            //2nd in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-45,
                    26, 13);

            //3rd out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-1-chargeCount*4/3-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-1-chargeCount*4/3-45,
                    36, 18);

            //3rd in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-chargeCount*4/3-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-chargeCount*4/3-45,
                    26, 13);

            //top out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-1-chargeCount*4/3-chargeCount*4/3-1-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-chargeCount*4/3-1-chargeCount*4/3-chargeCount*4/3-1-45,
                    36, 18);

            //top in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-chargeCount*4/3-chargeCount*4/3-1-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-chargeCount*4/3-1-chargeCount*4/3-chargeCount*4/3-1-45,
                    26, 13);

        }
        else { // maximum

            //bottom out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-45,
                    36, 18);

            //bottom in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-45,
                    26, 13);

            //rectangle bar
            g2.setColor(deepColor);
            g2.fillRect((int) super.location.x - 80 / 2 +27, (int) super.location.y+32-40*2-45,
                    26,40*2);
            g2.setColor(whiteColor);
            g2.drawLine((int) super.location.x - 80 / 2 +27,(int) super.location.y+32-40*2-45,
                    (int) super.location.x - 80 / 2 +27,(int) super.location.y+32-40*2+40*2-45);
            g2.drawLine((int) super.location.x - 80 / 2 +27+26,(int) super.location.y+32-40*2-45,
                    (int) super.location.x - 80 / 2 +27+26,(int) super.location.y+32-40*2+40*2-45);

            //2nd out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-45,
                    36, 18);

            //2nd in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-45,
                    26, 13);

            //3rd out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-26-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-26-45,
                    36, 18);

            //3rd in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-26-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-26-45,
                    26, 13);

            //top out oval
            g2.setColor(lightColor);
            g2.fillOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-26-27-45,
                    36, 18);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +22,
                    (int) super.location.y+30-27-26-27-45,
                    36, 18);

            //top in oval
            g2.setColor(deepColor);
            g2.fillOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-26-27-45,
                    26, 13);
            g2.setColor(whiteColor);
            g2.drawOval((int) super.location.x - 80 / 2 +27,
                    (int) super.location.y+32-27-26-27-45,
                    26, 13);


            //top oval center collision super.location.x - 80 / 2 +40, super.location.y+39 -80
        }

        chargeCount++;
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

    private void updateState() { // STATE ARE NOT WORKING HERE...

        if (state == STATE_CHARGING) {
            if (chargeCount >=25) {
                state = STATE_SHOOTING;
            }
            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                color = Color.WHITE;
                animStrategy = new WaterBeamAnimExploding(this);

            }
        }
        else if (state == STATE_SHOOTING ) { // travelling mode
            if (lobsterMon.currentLobsterMon != null) { // existing lobsterMon and chase mode
                if (hitCount == 0 && lobsterMon.currentLobsterMon.location.distance(location) <= 400.0) {
                    if (location.y>-10){
//                        state = STATE_CHASING;
//                        animStrategy = new WaterBeamAnimChasing(this);
//                    color = new Color(value1,value2,value3);
//                    System.out.println("testing1");
                    }
                }
            }

            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                color = Color.WHITE;
                animStrategy = new WaterBeamAnimExploding(this);

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

        else if (state == STATE_EXPLODING) {
            if (size >= MAX_BEAM_SIZE) {
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
        return size/2;
    }
}
