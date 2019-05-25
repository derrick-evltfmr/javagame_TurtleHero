package model;

import controller.Main;
import controller.PlayerInputEventQueue;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Main.gameData;

public class AngleLine extends GameFigure {

//    String text;
//    Color color;
//    Font font;


    int state;
    public static final int STATE_MOVING = 0;
    public static final int STATE_DONE = 1;

    Point2D.Float generateLocation;
    Color originalColor;
    int colorChange =0;

    public static boolean angleLineCountOn = false;
    public static int angleLineCount = 0;
    public static AngleLine currentAngleLine;

    public AngleLine(int x, int y) {
        super(x,y);
//        originalColor = color;
        state = STATE_MOVING;
        generateLocation = new Point2D.Float(x,y);

        currentAngleLine = this; // change to this when one generates
        angleLineCountOn = true; // set on when generates
        angleLineCount = 0; // set back to 0 when one generates

    }



    @Override
    public void render(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(150,150,150));

        float[] dash1 = { 2f, 0f, 2f };

        BasicStroke bs1 = new BasicStroke(2,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1.0f,
                dash1,
                2f);
        g2.setStroke(bs1);

        var turtlehero = gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
        switch (PlayerInputEventQueue.angleCount) {
            case 1:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0 *90 * Math.tan(Math.toRadians(10))),
                        (int)(location.y-120/100.0*90 )); //0.95
                break;

            case 2:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0*85.5 * Math.tan(Math.toRadians(20))),
                        (int)(location.y-120/100.0*85.5 )); //0.89
                break;

            case 3:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0*79 * Math.tan(Math.toRadians(30))),
                        (int)(location.y-120/100.0*79)); //0.78
                break;

            case 4:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0*68 * Math.tan(Math.toRadians(40))),
                        (int)(location.y-120/100.0*68 )); //0.675
                break;

            case 5:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0*58 * Math.tan(Math.toRadians(50))),
                        (int)(location.y-120/100.0*58 )); //0.58
                break;

            case 6:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/100.0*45 * Math.tan(Math.toRadians(60))),
                        (int)(location.y-120/100.0*45 )); //0.45
                break;

            case 7:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/200.0*61 * Math.tan(Math.toRadians(70))),
                        (int)(location.y-120/200.0*61 )); //0.305
                break;

            case 8:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+120/200.0*31 * Math.tan(Math.toRadians(80))),
                        (int)(location.y-120/200.0*31 )); //0.155
                break;

            case 9:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+105 * 1), (int)location.y-105/9*0);
                break;

            case 0:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x+110 * 0), (int)location.y-110 *1);
                break;

            case -1:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0 *90 * Math.tan(Math.toRadians(10))),
                        (int)(location.y-120/100.0*90 )); //0.95
                break;

            case -2:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0*85.5 * Math.tan(Math.toRadians(20))),
                        (int)(location.y-120/100.0*85.5 )); //0.89
                break;

            case -3:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0*79 * Math.tan(Math.toRadians(30))),
                        (int)(location.y-120/100.0*79 )); //0.78
                break;

            case -4:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0*68 * Math.tan(Math.toRadians(40))),
                        (int)(location.y-120/100.0*68 )); //0.68
                break;

            case -5:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0*58 * Math.tan(Math.toRadians(50))),
                        (int)(location.y-120/100.0*58 )); //0.58
                break;

            case -6:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/100.0*45 * Math.tan(Math.toRadians(60))),
                        (int)(location.y-120/100.0*45 )); //0.45
                break;

            case -7:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/200.0*61 * Math.tan(Math.toRadians(70))),
                        (int)(location.y-120/200.0*61 )); //0.305
                break;

            case -8:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-120/200.0*31 * Math.tan(Math.toRadians(80))),
                        (int)(location.y-120/200.0*31 )); //0.155
                break;

            case -9:
                g2.drawLine((int)location.x, (int)location.y,
                        (int)(location.x-105 * 1), (int)location.y-105/9*0);
                break;
        }


        g2.setStroke(new BasicStroke(2)); // reset in case some other graphics don't reset in the beginning
    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {
        if (state == STATE_MOVING) {
            var turtlehero = gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
            super.location.x = turtlehero.location.x -14; // almost the nose of turtle
            super.location.y = turtlehero.location.y +1; // almost the nose of turtle // refer to bubble not shield

//            if (hitCount >0 && !(hitByStrongCount >0) && enemyHP <=0) {
//                state = STATE_ESCAPING;
//                animStrategy = new lobsterMonAnimEscaping(this);
//                System.out.println("hi");
//            }
//            if (hitCount >0 && hitByStrongCount >0)  {
//                state = STATE_EXPLODE;
//                animStrategy = new lobsterMonAnimHurt(this);
//            }

            if ((angleLineCountOn==false && angleLineCount ==0) || currentAngleLine != this){ //count to 0 or another generates
                state = STATE_DONE;
            }

        }

        else if (state == STATE_DONE) {
            super.done = true;
//            Main.createUFO = true;
//
//            notifyEvent(); // lobsterMon died
        }

    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }
}

