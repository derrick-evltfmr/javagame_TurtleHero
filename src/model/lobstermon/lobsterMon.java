package model.lobstermon;

import controller.Main;
import controller.enemyobserver.EnemyObserver;
import controller.enemyobserver.EnemySubject;
import model.GameFigure;

import java.awt.*;
import java.util.ArrayList;

public class lobsterMon extends GameFigure implements EnemySubject {

    public static lobsterMon currentLobsterMon;

    public static int UNIT_MOVE = 3;
    public static int UNIT_MOVE_ESCAPING = 10;
    public static final int STATE_FORWARDING = 0;
    public static final int STATE_ESCAPING = 1;
    public static final int STATE_DONE = 2;
    public static final int STATE_REACHED = 4;

//    public static final int STATE_EXPLODE = 3;

    public int eye = 0;
    public final int EYES_NORMAL = 1;
    public final int EYES_FAINT = 2;

    int size = 40;
    int width, height;
//    boolean movingRight = true;

    int state;
    Color color;

    lobsterMonAnimStrategy animStrategy;


    ArrayList<EnemyObserver> listeners = new ArrayList<>();

    int isBigBoss = 0;
    int winWalk = 0;


    public lobsterMon(int x, int y) {
        super(x, y);
        width = size;
        height = size/2;

        state = STATE_FORWARDING; // initial state
        color = Color.BLUE;

        animStrategy = new lobsterMonAnimForwarding(this);
        lobsterMon.currentLobsterMon = this; // focus the current lobsterMon to this lobsterMon


        enemyINDEX = 1; //Lobster Monster
        enemyHP = 30;

        eye = EYES_NORMAL;

//        isBigBoss = 0;
    }

//    public lobsterMon(int x, int y, int size) { // BIG BOSS
//        super(x, y);
//        width = size;
//        height = size/2;
//
//        state = STATE_ENTERING; // initial state
//        color = new Color(90,90,90);
//
//        animStrategy = new lobsterMonAnimForwarding(this);
//
////        lobsterMon.currentLobsterMon = this;   // NOT FOCUS THE BOSS, SO THAT THE CHEATING MISSILE CAN'T CHASE THE BOSS
//
//        isBigBoss = 1;
//    }

    @Override
    public void render(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        //draw head
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillOval((int)location.x-38,(int)location.y,42,32);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawOval((int)location.x-38,(int)location.y,42,32);

        //draw left arm
        int[] x6 = {(int)location.x-39+10, (int)location.x-81+20, (int)location.x-62+10 , (int)location.x-44
                , (int)location.x-57+8, (int)location.x-33+5};
        int[] y6 = {(int)location.y-8,(int)location.y+20,(int)location.y+43 ,(int)location.y+38
                ,(int)location.y+20,(int)location.y+3};
        color = new Color(188,152,9); g2.setColor(color);// lobster body color3
        g2.fillPolygon(x6,y6,6);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x6,y6,6);

        //draw right arm
        int[] x7 = {(int)location.x-1-5, (int)location.x+31-5+1, (int)location.x+22-5, (int)location.x+14-5
                , (int)location.x+19-5, (int)location.x-2-5};
        int[] y7 = {(int)location.y-8,(int)location.y+20-1,(int)location.y+43 ,(int)location.y+38
                ,(int)location.y+20,(int)location.y+3};
        color = new Color(188,152,9); g2.setColor(color);// lobster body color3
        g2.fillPolygon(x7,y7,6);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x7,y7,6);

        //draw left hand
        int[] x8 = {(int)location.x-42, (int)location.x-57, (int)location.x-63 , (int)location.x-60
                , (int)location.x-44, (int)location.x-46, (int)location.x-33, (int)location.x-34};
        int[] y8 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillPolygon(x8,y8,8);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x8,y8,8);

        //draw right hand
        int[] x9 = {(int)location.x+6, (int)location.x+21, (int)location.x+27 , (int)location.x+24
                , (int)location.x+8, (int)location.x+10, (int)location.x-3, (int)location.x-2};
        int[] y9 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillPolygon(x9,y9,8);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x9,y9,8);

        //draw 1st body
        int[] x1 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
        int[] y1 = {(int)location.y+4+5,(int)location.y-14+5,(int)location.y-10+5 ,(int)location.y-27+5
                ,(int)location.y-10+5,(int)location.y-14+5 ,(int)location.y+4+5};
        color = new Color(248,172,9); g2.setColor(color);// lobster body color1
        g2.fillPolygon(x1,y1,7);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x1,y1,7);

        //draw 3rd body
        int[] x3 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
        int[] y3 = {(int)location.y+4+5-25,(int)location.y-14+5-25,(int)location.y-10+5-25 ,(int)location.y-27+5-25
                ,(int)location.y-10+5-25,(int)location.y-14+5-25 ,(int)location.y+4+5-25};
        color = new Color(188,152,9); g2.setColor(color);// lobster body color1
        g2.fillPolygon(x3,y3,7);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x3,y3,7);

        //draw 5th body
        int[] x5 = {(int)location.x-32+1, (int)location.x-28+1-5 , (int)location.x-18+1
                , (int)location.x-9+2+5 , (int)location.x-5+2};
        int[] y5 = {(int)location.y+4+5-40+10,(int)location.y-14+5-40-10+10 ,(int)location.y-27+5-40+20
                ,(int)location.y-14+5-40-10+10 ,(int)location.y+4+5-40+10};
        color = new Color(248,172,9); g2.setColor(color);// lobster body color1
        g2.fillPolygon(x5,y5,5);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x5,y5,5);

        //draw 4th body
        int[] x4 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
        int[] y4 = {(int)location.y+4+5-45,(int)location.y-14+5-45,(int)location.y-10+5-45 ,(int)location.y-27+5-45
                ,(int)location.y-10+5-45,(int)location.y-14+5-45 ,(int)location.y+4+5-45};
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillPolygon(x4,y4,7);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x4,y4,7);

        //draw 2nd body
        int[] x2 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
        int[] y2 = {(int)location.y+4+5-15,(int)location.y-14+5-15,(int)location.y-10+5-15 ,(int)location.y-27+5-15
                ,(int)location.y-10+5-15,(int)location.y-14+5-15 ,(int)location.y+4+5-15};
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillPolygon(x2,y2,7);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x2,y2,7);

        //draw horn
        int[] x10 = {(int)location.x-24, (int)location.x-23, (int)location.x-13 , (int)location.x-14};
        int[] y10 = {(int)location.y+15,(int)location.y+2,(int)location.y-1 ,(int)location.y+15};
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.fillPolygon(x10,y10,4);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x10,y10,4);
        color = new Color(248,138,9); g2.setColor(color);// lobster body color2
        g2.drawLine((int)location.x-24,(int)location.y+15,(int)location.x-14,(int)location.y+15);


        if (eye==EYES_NORMAL) {
            //draw eyes black
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 34 + 1, (int) location.y + 15, 10, 10);
            g2.fillOval((int) location.x - 13 + 1, (int) location.y + 15, 10, 10);

            //draw eyes white
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 34 + 6 - 1, (int) location.y + 20 - 2, 5, 5);
            g2.fillOval((int) location.x - 13 + 5, (int) location.y + 20 - 2, 5, 5);

            //draw eye brows
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawLine((int) location.x - 31, (int) location.y + 13, (int) location.x - 24, (int) location.y + 15);
            g2.drawLine((int) location.x - 12, (int) location.y + 16, (int) location.x - 5 + 1, (int) location.y + 14 - 1);

        } else if (eye == EYES_FAINT) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
//            g2.fillRect((int) location.x - 34 , (int) location.y + 12, 14,12);
//            g2.fillRect((int) location.x - 13 , (int) location.y + 12, 14, 12);

            g2.drawLine((int) location.x - 34 , (int) location.y + 12, (int) location.x - 34+9 , (int) location.y + 12+8);
            g2.drawLine((int) location.x - 34 , (int) location.y + 12+8, (int) location.x - 34+9 , (int) location.y + 12);

            g2.drawLine((int) location.x - 13 , (int) location.y + 12, (int) location.x - 13+9 , (int) location.y + 12+8);
            g2.drawLine((int) location.x - 13 , (int) location.y + 12+8, (int) location.x - 13+9 , (int) location.y + 12);

            g2.setStroke(new BasicStroke(2)); // reset
        }

        //draw mouth
        color = Color.BLACK; g2.setColor(color);
        g2.drawLine((int)location.x-19,(int)location.y+23,(int)location.x-26,(int)location.y+27);
        g2.drawLine((int)location.x-19,(int)location.y+23,(int)location.x-11,(int)location.y+26);

        if (eye==EYES_FAINT) {
            g2.setColor(Color.BLACK);
            g2.drawLine((int)location.x-19,(int)location.y+30,(int)location.x-26,(int)location.y+27);
            g2.drawLine((int)location.x-19,(int)location.y+30,(int)location.x-11,(int)location.y+26);

        }


    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate();

    }

    private void updateState() {
        if (state == STATE_FORWARDING) {
            if (hitCount >0 && enemyHP <=0) {  // && !(hitByStrongCount >0) removed
                state = STATE_ESCAPING;
                animStrategy = new lobsterMonAnimEscaping(this);
//                System.out.println("hi");
            }
//            if (hitCount >0 && hitByStrongCount >0)  {
////                state = STATE_EXPLODE;
////                animStrategy = new lobsterMonAnimHurt(this);                          // REVIEW LATER
//                state = STATE_ESCAPING;
//                animStrategy = new lobsterMonAnimEscaping(this);
//            }
            if ((location.y+62 > 680 || hitTheTurtleHero>0)&& canHurt ==true) {     // CAN HURT <- WHEN IT IS NOT ESCAPING
                state = STATE_REACHED;
                Main.gameOver = true;
                Main.endScreen_gameOver();
            }
        }
        else if (state == STATE_ESCAPING) {
            if (location.y < Main.win.canvas.height - 1500) {
                state = STATE_DONE;
            }
//            System.out.println(location.y + " " + Main.win.canvas.height );

        }
        else if (state == STATE_REACHED) {
            animStrategy = new lobsterMonAnimReached(this);
        }
        else if (state == STATE_DONE) {
            super.done = true;

            notifyEvent(); // lobsterMon died
        }

    }

    @Override
    public int getCollisionRadius() {
//        if (isBigBoss == 0) {
//            return (int)(size/2 *0.75);
//        }
//        else {
//            return (int)(100/2 *0.75); // when lobsterMon is big, need bigger collision radius
//        }

        return 30;
    }

    @Override
    public void attachListener(EnemyObserver o) {
        listeners.add(o);
    }

    @Override
    public void detachListener(EnemyObserver o) {
        listeners.remove(o);
    }

    @Override
    public void notifyEvent() {
        for (var o: listeners) {
            o.eventReceived();
        }

    }
}
