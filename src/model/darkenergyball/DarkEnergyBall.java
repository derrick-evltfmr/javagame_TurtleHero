package model.darkenergyball;

import controller.Main;
import model.GameFigure;
import model.darkenergyball.DarkEnergyBallAnimShooting;
import model.darkenergyball.DarkEnergyBallAnimStrategy;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

public class DarkEnergyBall extends GameFigure {


    int DB_UNITMOVE; // INITIALIZE IN CONSTRUCTOR
//    public static final int DB_UNIT_MOVE = 5;
    public static final int INIT_DB_EXPLOCDCOUNT = 0;
    public static final int MAX_DB_EXPLODECOUNT = 8;


    public static final int STATE_SHOOTING = 0;
    public static final int STATE_EXPLODING = 1;
    public static final int STATE_DONE = 2;


    int explodeCount = INIT_DB_EXPLOCDCOUNT;
    Point2D.Float target; // where mouse was pressed, get target location and draw the path towards it
    Point2D.Float longTarget;
    Color color; // color of the bubble

    int state;

    int startingx = 0;
    int startingy = 0;
    double theta;
    double rad;
    boolean rotateshoot = false;



    DarkEnergyBallAnimStrategy animStrategy;


    boolean endExplosion = false;

    Color purpleColor1 = new Color (90,68,110);
    Color purpleColor2 = new Color (118,64,164);
    Color purpleColor3 = new Color (60,20,96);
    Color purpleColor4 = new Color (35,34,36);


    Color lightColor = new Color (173,100,153); // purple2
    Color deepColor = new Color (123,0,183); // purple3
    Color lightMidColor = new Color (138,94,160); //purple2
    Color deepMidColor = new Color (100,20,96); // purple3
    Color lightPurpleColor = new Color (118,64,164); //purple2
    Color deepPurpleColor = new Color (60,20,96); // purple3

    //Color insideColorLight = new Color (204,153,255);
    //Color insideColorMid = new Color (104,53,155);
    Color insideColorDark = new Color (35,34,36);


    Color whitelineColor = new Color(233, 230, 224);
    Color violetlineColor = new Color (204,153,255);
    Color lineColor = new Color (204,153,255);

    int colorCount = 0;
    int colorSwitchCount = 0;
    int colorSwitchIndex = 0;

    int shootingDeviationx = 0;
    int shootingDeviationy = 0;


    public DarkEnergyBall(int startx, int starty) { // startx starty is from the shooting enemy

        turtleHero turtleHero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

        super.location.x = startx; // assigned by the enemy object
        super.location.y = starty; // assigned by the enemy object

        startingx = startx;
        startingy = starty;

        Random rand = new Random(); // newly add, for increasing the game difficulty
        shootingDeviationx = rand.nextInt(241)-120; // -120~120
        shootingDeviationy = rand.nextInt(241)-120; // -120~120

        Point2D.Float thLocation = new Point2D.Float(turtleHero.location.x -14 + shootingDeviationx, turtleHero.location.y +1+50 + shootingDeviationy); // turtle hero location as target
        rad = Math.atan2(thLocation.y - super.location.y, thLocation.x - super.location.x);


        theta = Math.toDegrees(Math.atan2(thLocation.y - super.location.y, thLocation.x - super.location.x));
        if (theta < 0.0) {
            theta += 360.0;
        }
//        System.out.println(theta);  //shooting down is 90

        if (theta<40.0||theta>140.0) {  // if angle > 50, then shoot to village
            target = new Point2D.Float(startx+10,starty+2000);
            rotateshoot = false;
        }
        else { // else, target turtle hero!
            target = new Point2D.Float((float) (thLocation.x + 2000 * Math.cos(rad)), ((float) (thLocation.y + 2000 * Math.sin(rad))));
            rotateshoot = true;
        }

        color = Color.RED; // initial color

        state = STATE_SHOOTING; // initial state

        animStrategy = new DarkEnergyBallAnimShooting(this); // pass the bubble obj itself to strategy

        enemyINDEX = 5;

        DB_UNITMOVE = 7;

    }

    @Override
    public void render(Graphics2D g2) {

//        g2.setColor(new Color(102,0,102));
//        g2.fillOval((int)location.x-80/2, (int)location.y-80/2, 80, 80);

//        switch(colorSwitchIndex){
//            case 0: g2.setColor(purpleColor1); break;
//            case 1: g2.setColor(purpleColor2); break;
//            case 2: g2.setColor(purpleColor3); break;
//            case 3: g2.setColor(purpleColor4); break;
//            case 4: g2.setColor(purpleColor5); break;

        if (chargeCount <= 5) { // charging
            // outside circle
            color = purpleColor1;
            g2.setColor(color);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 16 / 2, (int) super.location.y - chargeCount * 16 / 2,
                    chargeCount * 16, chargeCount * 16);
            // white circle edge
            color = new Color(233, 230, 224);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 16 / 2 + 6, (int) super.location.y - chargeCount * 16 / 2 + 6,
                    chargeCount * 16 - 12, chargeCount * 16 - 12);
            // 2nd circle
            color = purpleColor2;
            g2.setColor(color);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 16 / 2 + 7, (int) super.location.y - chargeCount * 16 / 2 + 7,
                    chargeCount * 16 - 14, chargeCount * 16 - 14);

            // 3rd circle
            color = purpleColor3;
            g2.setColor(color);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 16 / 2 + 7 + 7, (int) super.location.y - chargeCount * 16 / 2 + 7 + 7,
                    chargeCount * 16 - 14 - 14, chargeCount * 16 - 14 - 14);

            // insde circle
            color = purpleColor4;
            g2.setColor(color);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 16 / 2  + 7 + 7 + 7, (int) super.location.y - chargeCount * 16 / 2 + 7 + 7 + 7,
                    chargeCount * 16 - 14 - 14 - 14, chargeCount * 16 - 14 - 14 - 14);

        }   else if (state == STATE_EXPLODING) {

            // ouside circle
            color = purpleColor1;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 -explodeCount*5/2, (int) super.location.y - 80 / 2-explodeCount*5/2,
                    80+explodeCount*5, 80+explodeCount*5);
            // white circle edge
            color = new Color(233, 230, 224);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 6-explodeCount*5/2, (int) super.location.y - 80 / 2 + 6 -explodeCount*5/2,
                    80 - 12+explodeCount*5, 80 - 12+explodeCount*5);
            // 2nd circle
            color = purpleColor2;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7-explodeCount*5/2, (int) super.location.y - 80 / 2 + 7-explodeCount*5/2,
                    80 - 14+explodeCount*5, 80 - 14+explodeCount*5);

            // 3rd circle
            color = purpleColor3;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7 + 7-explodeCount*5/2, (int) super.location.y - 80 / 2 + 7 + 7-explodeCount*5/2,
                    80 - 14 - 14+explodeCount*5, 80 - 14 - 14+explodeCount*5);

            // inside circle
            color = purpleColor4;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7 + 7 + 7-explodeCount*5/2, (int) super.location.y - 80 / 2 + 7 + 7 + 7-explodeCount*5/2,
                    80 - 14 - 14 - 14+explodeCount*5, 80 - 14 - 14 - 14+explodeCount*5);

        }
        else { // maximum
            // ouside circle
            color = purpleColor1;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2, (int) super.location.y - 80 / 2, 80, 80);
            // white circle edge
            color = new Color(233, 230, 224);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 6, (int) super.location.y - 80 / 2 + 6,
                    80 - 12, 80 - 12);
            // 2nd circle
            color = purpleColor2;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7, (int) super.location.y - 80 / 2 + 7,
                    80 - 14, 80 - 14);

            // 3rd circle
            color = purpleColor3;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7 + 7, (int) super.location.y - 80 / 2 + 7 + 7,
                    80 - 14 - 14, 80 - 14 - 14);

            // inside circle
            color = purpleColor4;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 + 7 + 7 + 7, (int) super.location.y - 80 / 2 + 7 + 7 + 7,
                    80 - 14 - 14 - 14, 80 - 14 - 14 - 14);


        }
        chargeCount++;
        colorCount++;
        if (colorCount % 3 == 0) {
            colorSwitchCount++;
            if (colorSwitchCount % 2 == 0) {
//                if(colorSwitchOn == true) colorSwitchOn = false; // if two color like tick tock
//                else colorSwitchOn = true;
                if (colorSwitchIndex < 2) { // changing from 0, 1 (2colors)
                    colorSwitchIndex++;
                } else colorSwitchIndex = 0; // if =4, then set back to 0

                if (colorSwitchIndex == 0) {
                    purpleColor2 = lightPurpleColor;
                    purpleColor3 = deepPurpleColor;
                    purpleColor4 = insideColorDark;
                } else if(colorSwitchIndex==1){
                    purpleColor2 = lightMidColor;
                    purpleColor3 = deepMidColor;
//                    purpleColor4 = insideColorMid;
                } else {
                    purpleColor2 = lightColor;
                    purpleColor3 = deepColor;
                    purpleColor4 = Color.BLACK;
                }
            }
        }





//        }


//        if (!(state ==STATE_EXPLODING||state==STATE_DONE)) {
//
//            AffineTransform beforeRotate = g2.getTransform();
//
//            g2.setColor(color);
//            g2.setStroke(new BasicStroke(2)); // change the thickness back to 1
////        g2.fillOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
////        color = new Color(233,230,224); g2.setColor(color);
////        g2.drawOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
////        color = new Color(154,224,222);
////        g2.rotate(90,location.x,location.y);
//
////        g2.rotate((int)(theta), super.location.x, super.location.y);
//
//            AffineTransform transformTheta = new AffineTransform();
//            transformTheta.rotate(rad + 1.57, location.x, location.y);
//
//            //123D
//            int[] x1 = {(int) location.x + 0, (int) location.x - 10  , (int) location.x - 4 };
//            int[] y1 = {(int) location.y - 20 -7, (int) location.y - 12 , (int) location.y - 10 };
//            g2.setColor(deepColor);
//            Polygon xy1 = new Polygon(x1, y1, 3);
//            Shape xy1T = transformTheta.createTransformedShape(xy1);
//            if (rotateshoot == true) {g2.fill(xy1T); g2.setColor(lineColor); g2.draw(xy1T);}
//            else {g2.fill(xy1); g2.setColor(lineColor); g2.draw(xy1);}
//
////        g2.fillPolygon(x1,y1,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x1,y1,3);
////
////        //143L
//            int[] x2 = {(int) location.x + 0, (int) location.x + 4 , (int) location.x - 4 };
//            int[] y2 = {(int) location.y - 20 -7, (int) location.y - 10 , (int) location.y - 10 };
//            g2.setColor(lightColor);
////        g2.fillPolygon(x2,y2,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x2,y2,3);
//            Polygon xy2 = new Polygon(x2, y2, 3);
//            Shape xy2T = transformTheta.createTransformedShape(xy2);
//            if (rotateshoot == true) {g2.fill(xy2T); g2.setColor(lineColor); g2.draw(xy2T);}
//            else {g2.fill(xy2); g2.setColor(lineColor); g2.draw(xy2);}
//
//
////
////        //145D
//            int[] x3 = {(int) location.x + 0, (int) location.x + 4 , (int) location.x + 10 };
//            int[] y3 = {(int) location.y - 20-7, (int) location.y - 10 , (int) location.y - 12 };
//            g2.setColor(deepColor);
////        g2.fillPolygon(x3,y3,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x3,y3,3);
//            Polygon xy3 = new Polygon(x3, y3, 3);
//            Shape xy3T = transformTheta.createTransformedShape(xy3);
//            if (rotateshoot == true) {g2.fill(xy3T); g2.setColor(lineColor); g2.draw(xy3T);}
//            else {g2.fill(xy3); g2.setColor(lineColor); g2.draw(xy3);}
//
//
////
////        //23 1312 L
//            int[] x4 = {(int) location.x - 10 , (int) location.x - 4 , (int) location.x - 4, (int) location.x - 10-15};
//            int[] y4 = {(int) location.y - 12 , (int) location.y - 10 , (int) location.y + 0, (int) location.y + 0};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x4,y4,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x4,y4,4);
//            Polygon xy4 = new Polygon(x4, y4, 4);
//            Shape xy4T = transformTheta.createTransformedShape(xy4);
//            if (rotateshoot == true) {g2.fill(xy4T); g2.setColor(lineColor); g2.draw(xy4T);}
//            else {g2.fill(xy4); g2.setColor(lineColor); g2.draw(xy4);}
//
//
////
////        //43 1314 D
//            int[] x5 = {(int) location.x + 4 , (int) location.x - 4 , (int) location.x - 4, (int) location.x + 4};
//            int[] y5 = {(int) location.y - 10 , (int) location.y - 10 , (int) location.y + 0, (int) location.y + 0};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x5,y5,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x5,y5,4);
//            Polygon xy5 = new Polygon(x5, y5, 4);
//            Shape xy5T = transformTheta.createTransformedShape(xy5);
//            if (rotateshoot == true) {g2.fill(xy5T); g2.setColor(lineColor); g2.draw(xy5T);}
//            else {g2.fill(xy5); g2.setColor(lineColor); g2.draw(xy5);}
//
////
////        //45 1514 L
//            int[] x6 = {(int) location.x + 4 , (int) location.x + 10 , (int) location.x + 10+15, (int) location.x + 4};
//            int[] y6 = {(int) location.y - 10 , (int) location.y - 12 , (int) location.y + 0, (int) location.y + 0};
//        g2.setColor(lightColor);
////        g2.fillPolygon(x6,y6,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x6,y6,4);
//            Polygon xy6 = new Polygon(x6, y6, 4);
//            Shape xy6T = transformTheta.createTransformedShape(xy6);
//            if (rotateshoot == true) {g2.fill(xy6T); g2.setColor(lineColor); g2.draw(xy6T);}
//            else {g2.fill(xy6); g2.setColor(lineColor); g2.draw(xy6);}
//
////
////        //67 1312 L
//            int[] x7 = {(int) location.x - 10 , (int) location.x - 4 , (int) location.x - 4, (int) location.x - 10-15};
//            int[] y7 = {(int) location.y + 12 , (int) location.y + 10 , (int) location.y + 0, (int) location.y + 0};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x7,y7,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x7,y7,4);
//            Polygon xy7 = new Polygon(x7, y7, 4);
//            Shape xy7T = transformTheta.createTransformedShape(xy7);
//            if (rotateshoot == true) {g2.fill(xy7T); g2.setColor(lineColor); g2.draw(xy7T);}
//            else {g2.fill(xy7); g2.setColor(lineColor); g2.draw(xy7);}
//
////
////        //87 1314 D
//            int[] x8 = {(int) location.x + 4 , (int) location.x - 4 , (int) location.x - 4, (int) location.x + 4};
//            int[] y8 = {(int) location.y + 10 , (int) location.y + 10 , (int) location.y + 0, (int) location.y + 0};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x8,y8,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x8,y8,4);
//            Polygon xy8 = new Polygon(x8, y8, 4);
//            Shape xy8T = transformTheta.createTransformedShape(xy8);
//            if (rotateshoot == true) {g2.fill(xy8T); g2.setColor(lineColor); g2.draw(xy8T);}
//            else {g2.fill(xy8); g2.setColor(lineColor); g2.draw(xy8);}
//
////
////        //89 1514 L
//            int[] x9 = {(int) location.x + 4 , (int) location.x + 10 , (int) location.x + 10+15, (int) location.x + 4};
//            int[] y9 = {(int) location.y + 10 , (int) location.y + 12 , (int) location.y + 0, (int) location.y + 0};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x9,y9,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x9,y9,4);
//            Polygon xy9 = new Polygon(x9, y9, 4);
//            Shape xy9T = transformTheta.createTransformedShape(xy9);
//            if (rotateshoot == true) {g2.fill(xy9T); g2.setColor(lineColor); g2.draw(xy9T);}
//            else {g2.fill(xy9); g2.setColor(lineColor); g2.draw(xy9);}
//
////
////        //67 10 D
//            int[] x10 = {(int) location.x - 10 , (int) location.x - 4 , (int) location.x - 0};
//            int[] y10 = {(int) location.y + 12 , (int) location.y + 10 , (int) location.y + 20 +7};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x10,y10,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x10,y10,3);
//            Polygon xy10 = new Polygon(x10, y10, 3);
//            Shape xy10T = transformTheta.createTransformedShape(xy10);
//            if (rotateshoot == true) {g2.fill(xy10T); g2.setColor(lineColor); g2.draw(xy10T);}
//            else {g2.fill(xy10); g2.setColor(lineColor); g2.draw(xy10);}
//
////
////        //87 10 L
//            int[] x11 = {(int) location.x + 4 , (int) location.x - 4 , (int) location.x - 0};
//            int[] y11 = {(int) location.y + 10 , (int) location.y + 10 , (int) location.y + 20 +7};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x11,y11,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x11,y11,3);
//            Polygon xy11 = new Polygon(x11, y11, 3);
//            Shape xy11T = transformTheta.createTransformedShape(xy11);
//            if (rotateshoot == true) {g2.fill(xy11T); g2.setColor(lineColor); g2.draw(xy11T);}
//            else {g2.fill(xy11); g2.setColor(lineColor); g2.draw(xy11);}
//
////
////        //89 10 D
//            int[] x12 = {(int) location.x + 4 , (int) location.x + 10 , (int) location.x + 0};
//            int[] y12 = {(int) location.y + 10 , (int) location.y + 12 , (int) location.y + 20 +7};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x12,y12,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x12,y12,3);
//            Polygon xy12 = new Polygon(x12, y12, 3);
//            Shape xy12T = transformTheta.createTransformedShape(xy12);
//            if (rotateshoot == true) {g2.fill(xy12T); g2.setColor(lineColor); g2.draw(xy12T);}
//            else {g2.fill(xy12); g2.setColor(lineColor); g2.draw(xy12);}
//
//
//            if (rotateshoot == true) transformTheta.setTransform(beforeRotate);
//
//        }
//
//        else { // exploding
//
//
//            AffineTransform beforeRotate = g2.getTransform();
//
//            g2.setColor(color);
//            g2.setStroke(new BasicStroke(2)); // change the thickness back to 1
////        g2.fillOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
////        color = new Color(233,230,224); g2.setColor(color);
////        g2.drawOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
////        color = new Color(154,224,222);
////        g2.rotate(90,location.x,location.y);
//
////        g2.rotate((int)(theta), super.location.x, super.location.y);
//
//            AffineTransform transformTheta = new AffineTransform();
//            transformTheta.rotate(rad + 1.57, location.x, location.y);
//
//            //123D
//            int[] x1 = {(int) location.x + 0 -explodeCount, (int) location.x - 10-explodeCount, (int) location.x - 4-explodeCount};
//            int[] y1 = {(int) location.y - 20-explodeCount-7, (int) location.y - 12-explodeCount, (int) location.y - 10-explodeCount};
//            g2.setColor(deepColor);
//            Polygon xy1 = new Polygon(x1, y1, 3);
//            Shape xy1T = transformTheta.createTransformedShape(xy1);
//            if (rotateshoot == true) {g2.fill(xy1T); g2.setColor(lineColor); g2.draw(xy1T);}
//            else {g2.fill(xy1); g2.setColor(lineColor); g2.draw(xy1);}
//
////        g2.fillPolygon(x1,y1,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x1,y1,3);
////
////        //143L
//            int[] x2 = {(int) location.x + 0, (int) location.x + 4, (int) location.x - 4};
//            int[] y2 = {(int) location.y - 20-explodeCount-7, (int) location.y - 10-explodeCount, (int) location.y - 10-explodeCount};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x2,y2,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x2,y2,3);
//            Polygon xy2 = new Polygon(x2, y2, 3);
//            Shape xy2T = transformTheta.createTransformedShape(xy2);
//            if (rotateshoot == true) {g2.fill(xy2T); g2.setColor(lineColor); g2.draw(xy2T);}
//            else {g2.fill(xy2); g2.setColor(lineColor); g2.draw(xy2);}
//
//
////
////        //145D
//            int[] x3 = {(int) location.x + 0 +explodeCount, (int) location.x + 4 +explodeCount, (int) location.x + 10 +explodeCount};
//            int[] y3 = {(int) location.y - 20-explodeCount-7, (int) location.y - 10-explodeCount, (int) location.y - 12-explodeCount};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x3,y3,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x3,y3,3);
//            Polygon xy3 = new Polygon(x3, y3, 3);
//            Shape xy3T = transformTheta.createTransformedShape(xy3);
//            if (rotateshoot == true) {g2.fill(xy3T); g2.setColor(lineColor); g2.draw(xy3T);}
//            else {g2.fill(xy3); g2.setColor(lineColor); g2.draw(xy3);}
//
//
////
////        //23 1312 L
//            int[] x4 = {(int) location.x - 10-explodeCount, (int) location.x - 4-explodeCount, (int) location.x - 4-explodeCount, (int) location.x - 10-explodeCount-15};
//            int[] y4 = {(int) location.y - 12-explodeCount/2, (int) location.y - 10-explodeCount/2, (int) location.y + 0-explodeCount/2, (int) location.y + 0-explodeCount/2};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x4,y4,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x4,y4,4);
//            Polygon xy4 = new Polygon(x4, y4, 4);
//            Shape xy4T = transformTheta.createTransformedShape(xy4);
//            if (rotateshoot == true) {g2.fill(xy4T); g2.setColor(lineColor); g2.draw(xy4T);}
//            else {g2.fill(xy4); g2.setColor(lineColor); g2.draw(xy4);}
//
//
////
////        //43 1314 D
//            int[] x5 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 4, (int) location.x + 4};
//            int[] y5 = {(int) location.y - 10-explodeCount/2, (int) location.y - 10-explodeCount/2, (int) location.y-explodeCount/2 + 0, (int) location.y + 0-explodeCount/2};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x5,y5,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x5,y5,4);
//            Polygon xy5 = new Polygon(x5, y5, 4);
//            Shape xy5T = transformTheta.createTransformedShape(xy5);
//            if (rotateshoot == true) {g2.fill(xy5T); g2.setColor(lineColor); g2.draw(xy5T);}
//            else {g2.fill(xy5); g2.setColor(lineColor); g2.draw(xy5);}
//
////
////        //45 1514 L
//            int[] x6 = {(int) location.x + 4+explodeCount, (int) location.x + 10+explodeCount, (int) location.x + 10+explodeCount+15, (int) location.x + 4 +explodeCount};
//            int[] y6 = {(int) location.y - 10-explodeCount/2, (int) location.y - 12-explodeCount/2, (int) location.y + 0-explodeCount/2, (int) location.y + 0-explodeCount/2};
////        g2.setColor(lightColor);
////        g2.fillPolygon(x6,y6,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x6,y6,4);
//            Polygon xy6 = new Polygon(x6, y6, 4);
//            Shape xy6T = transformTheta.createTransformedShape(xy6);
//            if (rotateshoot == true) {g2.fill(xy6T); g2.setColor(lineColor); g2.draw(xy6T);}
//            else {g2.fill(xy6); g2.setColor(lineColor); g2.draw(xy6);}
//
////
////        //67 1312 L
//            int[] x7 = {(int) location.x - 10-explodeCount, (int) location.x - 4-explodeCount, (int) location.x - 4-explodeCount, (int) location.x - 10-explodeCount-15};
//            int[] y7 = {(int) location.y + 12+explodeCount/2, (int) location.y + 10+explodeCount/2, (int) location.y + 0+explodeCount/2, (int) location.y + 0+explodeCount/2};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x7,y7,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x7,y7,4);
//            Polygon xy7 = new Polygon(x7, y7, 4);
//            Shape xy7T = transformTheta.createTransformedShape(xy7);
//            if (rotateshoot == true) {g2.fill(xy7T); g2.setColor(lineColor); g2.draw(xy7T);}
//            else {g2.fill(xy7); g2.setColor(lineColor); g2.draw(xy7);}
//
//
////
////        //87 1314 D
//            int[] x8 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 4, (int) location.x + 4};
//            int[] y8 = {(int) location.y + 10+explodeCount/2, (int) location.y + 10+explodeCount/2, (int) location.y + 0+explodeCount/2, (int) location.y + 0+explodeCount/2};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x8,y8,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x8,y8,4);
//            Polygon xy8 = new Polygon(x8, y8, 4);
//            Shape xy8T = transformTheta.createTransformedShape(xy8);
//            if (rotateshoot == true) {g2.fill(xy8T); g2.setColor(lineColor); g2.draw(xy8T);}
//            else {g2.fill(xy8); g2.setColor(lineColor); g2.draw(xy8);}
//
////
////        //89 1514 L
//            int[] x9 = {(int) location.x + 4+explodeCount, (int) location.x + 10+explodeCount, (int) location.x + 10+explodeCount+15, (int) location.x + 4+explodeCount};
//            int[] y9 = {(int) location.y + 10+explodeCount/2, (int) location.y + 12+explodeCount/2, (int) location.y + 0+explodeCount/2, (int) location.y + 0+explodeCount/2};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x9,y9,4);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x9,y9,4);
//            Polygon xy9 = new Polygon(x9, y9, 4);
//            Shape xy9T = transformTheta.createTransformedShape(xy9);
//            if (rotateshoot == true) {g2.fill(xy9T); g2.setColor(lineColor); g2.draw(xy9T);}
//            else {g2.fill(xy9); g2.setColor(lineColor); g2.draw(xy9);}
//
//
////
////        //67 10 D
//            int[] x10 = {(int) location.x - 10 -explodeCount, (int) location.x - 4-explodeCount, (int) location.x - 0-explodeCount};
//            int[] y10 = {(int) location.y + 12+explodeCount, (int) location.y + 10+explodeCount, (int) location.y + 20+explodeCount+7};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x10,y10,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x10,y10,3);
//            Polygon xy10 = new Polygon(x10, y10, 3);
//            Shape xy10T = transformTheta.createTransformedShape(xy10);
//            if (rotateshoot == true) {g2.fill(xy10T); g2.setColor(lineColor); g2.draw(xy10T);}
//            else {g2.fill(xy10); g2.setColor(lineColor); g2.draw(xy10);}
//
////
////        //87 10 L
//            int[] x11 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 0};
//            int[] y11 = {(int) location.y + 10+explodeCount, (int) location.y + 10+explodeCount, (int) location.y + 20+explodeCount+7};
//            g2.setColor(lightColor);
////        g2.fillPolygon(x11,y11,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x11,y11,3);
//            Polygon xy11 = new Polygon(x11, y11, 3);
//            Shape xy11T = transformTheta.createTransformedShape(xy11);
//            if (rotateshoot == true) {g2.fill(xy11T); g2.setColor(lineColor); g2.draw(xy11T);}
//            else {g2.fill(xy11); g2.setColor(lineColor); g2.draw(xy11);}
//
//
////
////        //89 10 D
//            int[] x12 = {(int) location.x + 4+explodeCount, (int) location.x + 10+explodeCount, (int) location.x + 0+explodeCount};
//            int[] y12 = {(int) location.y + 10+explodeCount, (int) location.y + 12+explodeCount, (int) location.y + 20+explodeCount+7};
//            g2.setColor(deepColor);
////        g2.fillPolygon(x12,y12,3);
////        g2.setColor(whiteColor);
////        g2.drawPolygon(x12,y12,3);
//            Polygon xy12 = new Polygon(x12, y12, 3);
//            Shape xy12T = transformTheta.createTransformedShape(xy12);
//            if (rotateshoot == true) {g2.fill(xy12T); g2.setColor(lineColor); g2.draw(xy12T);}
//            else {g2.fill(xy12); g2.setColor(lineColor); g2.draw(xy12);}
//
//
//            if (rotateshoot == true) transformTheta.setTransform(beforeRotate);
//
//        }




    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate(); // Strategy design pattern

//        if (state == STATE_DEFENSING) {
//            double rad = Math.atan2(target.y - location.y, target.x - location.x);
//            location.x += UNIT_MOVE * Math.cos(rad); // opposite(delta x)/hypotenuse
//            location.y += UNIT_MOVE * Math.sin(rad); // adjacent(delta y)/hypotenuse
//        }
//        else if (state == STATE_DISAPPEARING) {
//            color = Color.YELLOW;
//            size++;
//        }

//
    }

    private void updateState() {
        //updated turtlehero location
        turtleHero turtleHero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
        Point2D.Float thLocation = new Point2D.Float(turtleHero.location.x -14, turtleHero.location.y +1+20);

        if (state == STATE_SHOOTING ) { // travelling mode
            if (hitCount > 0 || thLocation.distance(location) <= 10.0 || location.y+0 > 680) {
                state = STATE_EXPLODING;
                animStrategy = new model.darkenergyball.DarkEnergyBallAnimExploding(this);
                if (location.y+0 >680){
                    hitTheVillage++;
                    if (hitTheVillage ==1){
                        turtleHero.villageLife--;
                        if (turtleHero.villageLife ==0){
                            Main.gameOver = true;
                            if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                                Main.endScreen_gameOver();
                                Main.calledGameOver = true;
                            }
                        }
                    }
                }
            }
        }
        else if (state == STATE_EXPLODING) {
            if (explodeCount >= MAX_DB_EXPLODECOUNT) {
                state = STATE_DONE;
            }
        }
        else if (state == STATE_DONE) {
            super.done = true;
        }

    }

    @Override
    public int getCollisionRadius() {
        return 80/2;
    }
}
