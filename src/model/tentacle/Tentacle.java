package model.tentacle;

import controller.Main;
import controller.enemyobserver.EnemyObserver;
import controller.enemyobserver.EnemySubject;
import model.GameFigure;
import model.dsdevil.dsDevil;
import model.tentacle.TentacleAnimEscaping;
import model.tentacle.TentacleAnimForwarding;
import model.tentacle.TentacleAnimReached;
import model.tentacle.TentacleAnimStrategy;

import java.awt.*;
import java.util.ArrayList;

public class Tentacle extends GameFigure implements EnemySubject {

    public static Tentacle currentLobsterMon;

    public static int UNIT_MOVE = 2;
    public static int UNIT_MOVE_ESCAPING = 15;
    public static final int STATE_FORWARDING = 0;
    public static final int STATE_ESCAPING = 1;
    public static final int STATE_DONE = 2;
    public static final int STATE_REACHED = 4;

    public static final int STATE_EXPLODE = 3;

    int size = 40;
    int width, height;
//    boolean movingRight = true;

    int state;
    Color color;

    public int originalY = 0;

    Color darkGrayColor = new Color(51,51,51);
    Color crownColor = new Color (131,131,175);
    Color deepGrayColor = new Color(119,114, 113);
    Color lightGrayColor = new Color(174,170,169);
    Color darkColor = new Color (45,38,55);

    TentacleAnimStrategy animStrategy;


    ArrayList<EnemyObserver> listeners = new ArrayList<>();

    int isBigBoss = 0;
    int winWalk = 0;


    public Tentacle(int x, int y) {
        super(x, y);
        width = size;
        height = size/2;

        state = STATE_FORWARDING; // initial state
        color = Color.BLUE;

        animStrategy = new TentacleAnimForwarding(this);
        Tentacle.currentLobsterMon = this; // focus the current Tentacle to this Tentacle


        enemyINDEX = 6; //Tentacle
        enemyHP = 40;
        originalY = y;

//        isBigBoss = 0;
    }

//    public Tentacle(int x, int y, int size) { // BIG BOSS
//        super(x, y);
//        width = size;
//        height = size/2;
//
//        state = STATE_ENTERING; // initial state
//        color = new Color(90,90,90);
//
//        animStrategy = new TentacleAnimForwarding(this);
//
////        Tentacle.currentLobsterMon = this;   // NOT FOCUS THE BOSS, SO THAT THE CHEATING MISSILE CAN'T CHASE THE BOSS
//
//        isBigBoss = 1;
//    }

    @Override
    public void render(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));

        // keep for testing
//        //draw head
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillOval((int)location.x-38,(int)location.y,42,32);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawOval((int)location.x-38,(int)location.y,42,32);
//
//        //draw left arm
//        int[] x6 = {(int)location.x-39+10, (int)location.x-81+20, (int)location.x-62+10 , (int)location.x-44
//                , (int)location.x-57+8, (int)location.x-33+5};
//        int[] y6 = {(int)location.y-8,(int)location.y+20,(int)location.y+43 ,(int)location.y+38
//                ,(int)location.y+20,(int)location.y+3};
//        color = new Color(188,152,159); g2.setColor(color);// lobster body color3
//        g2.fillPolygon(x6,y6,6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x6,y6,6);
//
//        //draw right arm
//        int[] x7 = {(int)location.x-1-5, (int)location.x+31-5+1, (int)location.x+22-5, (int)location.x+14-5
//                , (int)location.x+19-5, (int)location.x-2-5};
//        int[] y7 = {(int)location.y-8,(int)location.y+20-1,(int)location.y+43 ,(int)location.y+38
//                ,(int)location.y+20,(int)location.y+3};
//        color = new Color(188,152,159); g2.setColor(color);// lobster body color3
//        g2.fillPolygon(x7,y7,6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x7,y7,6);
//
//        //draw left hand
//        int[] x8 = {(int)location.x-42, (int)location.x-57, (int)location.x-63 , (int)location.x-60
//                , (int)location.x-44, (int)location.x-46, (int)location.x-33, (int)location.x-34};
//        int[] y8 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
//                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x8,y8,8);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x8,y8,8);
//
//        //draw right hand
//        int[] x9 = {(int)location.x+6, (int)location.x+21, (int)location.x+27 , (int)location.x+24
//                , (int)location.x+8, (int)location.x+10, (int)location.x-3, (int)location.x-2};
//        int[] y9 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
//                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x9,y9,8);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x9,y9,8);
//
//        //draw 1st body
//        int[] x1 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y1 = {(int)location.y+4+5,(int)location.y-14+5,(int)location.y-10+5 ,(int)location.y-27+5
//                ,(int)location.y-10+5,(int)location.y-14+5 ,(int)location.y+4+5};
//        color = new Color(148,172,159); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x1,y1,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x1,y1,7);
//
//        //draw 3rd body
//        int[] x3 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y3 = {(int)location.y+4+5-25,(int)location.y-14+5-25,(int)location.y-10+5-25 ,(int)location.y-27+5-25
//                ,(int)location.y-10+5-25,(int)location.y-14+5-25 ,(int)location.y+4+5-25};
//        color = new Color(188,152,159); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x3,y3,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x3,y3,7);
//
//        //draw 5th body
//        int[] x5 = {(int)location.x-32+1, (int)location.x-28+1-5 , (int)location.x-18+1
//                , (int)location.x-9+2+5 , (int)location.x-5+2};
//        int[] y5 = {(int)location.y+4+5-40+10,(int)location.y-14+5-40-10+10 ,(int)location.y-27+5-40+20
//                ,(int)location.y-14+5-40-10+10 ,(int)location.y+4+5-40+10};
//        color = new Color(148,172,159); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x5,y5,5);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x5,y5,5);
//
//        //draw 4th body
//        int[] x4 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y4 = {(int)location.y+4+5-45,(int)location.y-14+5-45,(int)location.y-10+5-45 ,(int)location.y-27+5-45
//                ,(int)location.y-10+5-45,(int)location.y-14+5-45 ,(int)location.y+4+5-45};
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x4,y4,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x4,y4,7);
//
//        //draw 2nd body
//        int[] x2 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y2 = {(int)location.y+4+5-15,(int)location.y-14+5-15,(int)location.y-10+5-15 ,(int)location.y-27+5-15
//                ,(int)location.y-10+5-15,(int)location.y-14+5-15 ,(int)location.y+4+5-15};
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x2,y2,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x2,y2,7);
//
//        //draw horn
//        int[] x10 = {(int)location.x-24, (int)location.x-23, (int)location.x-13 , (int)location.x-14};
//        int[] y10 = {(int)location.y+15,(int)location.y+2,(int)location.y-1 ,(int)location.y+15};
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x10,y10,4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x10,y10,4);
//        color = new Color(148,138,159); g2.setColor(color);// lobster body color2
//        g2.drawLine((int)location.x-24,(int)location.y+15,(int)location.x-14,(int)location.y+15);
//
//        //draw eyes black
//        color = Color.BLACK; g2.setColor(color);
//        g2.fillOval((int)location.x-34+1,(int)location.y+15,10,10);
//        g2.fillOval((int)location.x-13+1,(int)location.y+15,10,10);
//
//        //draw eyes white
//        color = Color.WHITE; g2.setColor(color);
//        g2.fillOval((int)location.x-34+6-1,(int)location.y+20-2,5,5);
//        g2.fillOval((int)location.x-13+5,(int)location.y+20-2,5,5);
//
//        //draw eye brows
//        color = Color.BLACK; g2.setColor(color);
//        g2.drawLine((int)location.x-31,(int)location.y+13,(int)location.x-24,(int)location.y+15);
//        g2.drawLine((int)location.x-12,(int)location.y+16,(int)location.x-5+1,(int)location.y+14-1);
//
//        //draw mouth
//        color = Color.BLACK; g2.setColor(color);
//        g2.drawLine((int)location.x-19,(int)location.y+23,(int)location.x-26,(int)location.y+27);
//        g2.drawLine((int)location.x-19,(int)location.y+23,(int)location.x-11,(int)location.y+26);

        //=================================================================
//        g2.setColor(Color.lightGray);
//        g2.drawRect((int)location.x-100/2,(int)location.y-100/2,100,100);

        g2.setColor(darkColor);
        g2.fillRect((int)location.x-100/2,originalY-100/2,100,(int)location.y - originalY);

        g2.setColor(Color.lightGray);
        g2.drawRect((int)location.x-100/2,originalY-100/2,100,(int)location.y - originalY);

        //
        int[] x11 = {(int)location.x-15, (int)location.x+0, (int)location.x+15};
        int[] y11 = {(int)location.y+75, (int)location.y+75+50,(int)location.y+75};
        g2.setColor(darkGrayColor);
        g2.fillPolygon(x11,y11,3);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x11,y11,3);

        int[] x12 = {(int)location.x-40-10, (int)location.x-35-30, (int)location.x-20+10};
        int[] y12 = {(int)location.y+40, (int)location.y+45+50,(int)location.y+50};
        g2.setColor(darkGrayColor);
        g2.fillPolygon(x12,y12,3);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x12,y12,3);

        int[] x13 = {(int)location.x+40+10, (int)location.x+35+30, (int)location.x+20-10};
        int[] y13 = {(int)location.y+40, (int)location.y+45+50,(int)location.y+50};
        g2.setColor(darkGrayColor);
        g2.fillPolygon(x13,y13,3);
        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.drawPolygon(x13,y13,3);


        g2.setColor(crownColor);
        g2.fillOval((int)location.x-80, (int)location.y-125,160,200);

        g2.setColor(Color.lightGray);
        g2.drawOval((int)location.x-80, (int)location.y-125,160,200);

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
                canHurt = false;
                animStrategy = new TentacleAnimEscaping(this);
//                System.out.println("hi");
            }

            System.out.println(dsDevil.currentBoss.state);
            if (dsDevil.currentBoss.state == dsDevil.STATE_ESCAPING) { //note that dsDevil escaping and tentacle escaping INDEX are not the same= =
                state = STATE_ESCAPING;
                canHurt = false;
                animStrategy = new TentacleAnimEscaping(this);
            }

//            if (hitCount >0 && hitByStrongCount >0)  {
////                state = STATE_EXPLODE;
////                animStrategy = new TentacleAnimHurt(this);                          // REVIEW LATER
//                state = STATE_ESCAPING;
//                animStrategy = new TentacleAnimEscaping(this);
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
            animStrategy = new TentacleAnimReached(this);
        }
        else if (state == STATE_DONE) {


//            notifyEvent(); // Tentacle died // tracking each tentacle
            if(dsDevil.currenttentacle1 == this) {
                dsDevil.tentacle1AttackingEnded = true;
                dsDevil.currenttentacle1 = null;
            }
            if(dsDevil.currenttentacle2 == this) {
                dsDevil.tentacle2AttackingEnded = true;
                dsDevil.currenttentacle2 = null;
            }
            if(dsDevil.currenttentacle3 == this) {
                dsDevil.tentacle3AttackingEnded = true;
                dsDevil.currenttentacle3 = null;
            }
            if(dsDevil.currenttentacle4 == this) {
                dsDevil.tentacle4AttackingEnded = true;
                dsDevil.currenttentacle4 = null;
            }



            super.done = true;
        }

    }

    @Override
    public int getCollisionRadius() {
//        if (isBigBoss == 0) {
//            return (int)(size/2 *0.75);
//        }
//        else {
//            return (int)(100/2 *0.75); // when Tentacle is big, need bigger collision radius
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
