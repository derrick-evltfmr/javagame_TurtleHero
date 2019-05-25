package model.piranhamon;

import controller.Main;
import controller.enemyobserver.EnemyObserver;

import controller.enemyobserver.EnemySubject;
import model.GameFigure;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class piranhaMon extends GameFigure implements EnemySubject {

    public static piranhaMon currentPiranhaMon1;
    public static piranhaMon currentPiranhaMon2;

    public static boolean piranha1AttackingOn = false;
    public static boolean piranha2AttackingOn = false;

    public static TimerTask ttPiranha1attack;
    public static TimerTask ttPiranha2attack;

    public static int piranha1AttackCoolDown = 0;
    public static boolean piranha1AttackCDOn = false;
    public static int piranha2AttackCoolDown = 0;
    public static boolean piranha2AttackCDOn = false;

    public static int callBigBoss = 0; //each piranha mon dies ++, if it == 2, then call the DSdevil


    public static int UNIT_MOVE = 1; // slower than lob mon
    public static int UNIT_MOVE_ESCAPING = 12;
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

    Color deepGrayColor = new Color(119,114, 113);
    Color lightGrayColor = new Color(174,170,169);
    Color whiteColor = new Color (233,230,224);
    Color mouthColor = new Color (247,96,50);

    int eyeMoveCount = 0;
    int eyeMoveLocation = 0;

    piranhaMonAnimStrategy animStrategy;


    ArrayList<EnemyObserver> listeners = new ArrayList<>();   // CHECK IF THIS NEED TO REVISE LATER

    int isBigBoss = 0;
    int winWalk = 0;

    public piranhaMon(int x, int y) {
        super(x, y);
        width = size;
        height = size/2;

        state = STATE_FORWARDING; // initial state
        color = Color.BLUE;

        animStrategy = new piranhaMonAnimForwarding(this);
        if (piranhaMon.currentPiranhaMon1 == null) piranhaMon.currentPiranhaMon1 = this; // focus the current piranhaMon to this piranhaMon
        else piranhaMon.currentPiranhaMon2 = this;

        enemyINDEX = 2; //Piranha Monster
        enemyHP = 75;

        eye = EYES_NORMAL;
//        isBigBoss = 0;
    }

//    public lobsterMon(int x, int y, int size) { // BIG BOSS
//        super(x, y);
//        width = size;
//        height = size/2;
//
//        state = STATE_FORWARDING; // initial state
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

        //draw tail
        int[] x1 = {(int)location.x-79+5, (int)location.x-42, (int)location.x+16 , (int)location.x+48-5+4
                , (int)location.x+36+5, (int)location.x+1, (int)location.x+13, (int)location.x-41
                ,(int)location.x-29, (int)location.x-69};
        int[] y1 = {(int)location.y-3+10-10,(int)location.y+33+10,(int)location.y+33+10 ,(int)location.y-3+10-10
                ,(int)location.y-33+10,(int)location.y-7+10,(int)location.y-44+10+10,(int)location.y-44+10+10
                ,(int)location.y-7+10,(int)location.y-33+10};
        g2.setColor(lightGrayColor);
        g2.fillPolygon(x1,y1,10);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x1,y1,10);

        //draw left hand
        int[] x2 = {(int)location.x-97, (int)location.x-47, (int)location.x-50 , (int)location.x-110+3};
        int[] y2 = {(int)location.y+50,(int)location.y+50,(int)location.y+112 ,(int)location.y+66};
        g2.setColor(lightGrayColor);
        g2.fillPolygon(x2,y2,4);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x2,y2,4);

        //draw right hand
        int[] x3 = {(int)location.x+73-3, (int)location.x+23-3, (int)location.x+26-3 , (int)location.x+86-3-3};
        int[] y3 = {(int)location.y+50,(int)location.y+50,(int)location.y+112 ,(int)location.y+66};
        g2.setColor(lightGrayColor);
        g2.fillPolygon(x3,y3,4);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x3,y3,4);

        //draw chin
        int[] x4 = {(int)location.x-45-4, (int)location.x-39-4+2, (int)location.x+14+1-2 , (int)location.x+20+1};
        int[] y4 = {(int)location.y+116+5,(int)location.y+156+5,(int)location.y+156+5 ,(int)location.y+116+5};
        g2.setColor(lightGrayColor);
        g2.fillPolygon(x4,y4,4);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x4,y4,4);

        //draw mouth
        int[] x5 = {(int)location.x-45-4+5, (int)location.x-39-4+2+5, (int)location.x+14+1-2-5 , (int)location.x+20+1-5};
        int[] y5 = {(int)location.y+116+5+5,(int)location.y+156+5-3,(int)location.y+156+5-3 ,(int)location.y+116+5+5};
        g2.setColor(mouthColor);
        g2.fillPolygon(x5,y5,4);

        //draw upper teeth
        int[] x6 = {(int)location.x-42, (int)location.x-37, (int)location.x-34 , (int)location.x-31
                , (int)location.x-28, (int)location.x-25, (int)location.x-22, (int)location.x-18
                ,(int)location.x-15, (int)location.x-11, (int)location.x-7 , (int)location.x-3
                , (int)location.x+0, (int)location.x+3, (int)location.x+6, (int)location.x+9
                ,(int)location.x+14};
        int[] y6 = {(int)location.y+126,(int)location.y+145,(int)location.y+141 ,(int)location.y+146
                ,(int)location.y+143,(int)location.y+148,(int)location.y+145,(int)location.y+150
                ,(int)location.y+144,(int)location.y+150,(int)location.y+144 ,(int)location.y+149
                ,(int)location.y+143,(int)location.y+148,(int)location.y+141,(int)location.y+147
                ,(int)location.y+126};
        g2.setColor(Color.WHITE);
        g2.fillPolygon(x6,y6,17);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(x6,y6,17);

        //draw lower teeth
        int[] x7 = {(int)location.x-36, (int)location.x-32, (int)location.x-29 , (int)location.x-26
                , (int)location.x-21, (int)location.x-18, (int)location.x-15, (int)location.x-11
                ,(int)location.x-8, (int)location.x-3, (int)location.x+1 , (int)location.x+4
                ,(int)location.x+7};
        int[] y7 = {(int)location.y+157,(int)location.y+149,(int)location.y+154 ,(int)location.y+148
                ,(int)location.y+153,(int)location.y+148,(int)location.y+153,(int)location.y+149
                ,(int)location.y+154,(int)location.y+148,(int)location.y+153 ,(int)location.y+149
                ,(int)location.y+157};
        g2.setColor(Color.WHITE);
        g2.fillPolygon(x7,y7,13);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(x7,y7,13);


        //draw body oval
        g2.setColor(deepGrayColor);// piranha body color
        g2.fillOval((int)location.x-38-24,(int)location.y,96,144);
        g2.setColor(whiteColor); // white outline
        g2.drawOval((int)location.x-38-24,(int)location.y,96,144);

        //draw eyes

        if (eye == EYES_NORMAL) {

            g2.setColor(Color.BLACK);
            g2.fillOval((int) location.x - 38 - 4, (int) location.y + 116, 20, 13);
            g2.fillOval((int) location.x - 4 - 1, (int) location.y + 116, 20, 13);


            if (eyeMoveLocation == 0) {
                g2.setColor(whiteColor);
                g2.fillOval((int) location.x - 38 - 1, (int) location.y + 116 + 2, 8, 8);
                g2.fillOval((int) location.x - 4 + 1 + 2, (int) location.y + 116 + 2, 8, 8);

                g2.setColor(lightGrayColor);
                g2.drawOval((int) location.x - 38 - 1, (int) location.y + 116 + 2, 8, 8);
                g2.drawOval((int) location.x - 4 + 1 + 2, (int) location.y + 116 + 2, 8, 8);

//            g2.setColor(Color.BLACK); // TOO SCARY LOOK =.= HIDE THIS
//            g2.fillOval((int) location.x - 38-1+2 , (int) location.y + 116 + 2+2, 6, 6);
//            g2.fillOval((int) location.x - 4 + 1 +2+2 , (int) location.y + 116 + 2+2, 6, 6);

            } else if (eyeMoveLocation == 1 || eyeMoveLocation == 3) {
                g2.setColor(whiteColor);
                g2.fillOval((int) location.x - 38 - 1 + 2, (int) location.y + 116 + 2, 8, 8);
                g2.fillOval((int) location.x - 4 + 1 + 2 + 2, (int) location.y + 116 + 2, 8, 8);

                g2.setColor(lightGrayColor);
                g2.drawOval((int) location.x - 38 - 1 + 2, (int) location.y + 116 + 2, 8, 8);
                g2.drawOval((int) location.x - 4 + 1 + 2 + 2, (int) location.y + 116 + 2, 8, 8);

//            g2.setColor(Color.BLACK); // TOO SCARY LOOK =.= HIDE THIS
//            g2.fillOval((int) location.x - 38-1+2 +2, (int) location.y + 116 + 2+2, 6, 6);
//            g2.fillOval((int) location.x - 4 + 1 +2+2 +2 , (int) location.y + 116 + 2+2, 6, 6);
            } else {
                g2.setColor(whiteColor);
                g2.fillOval((int) location.x - 38 - 1 + 2 + 3, (int) location.y + 116 + 2, 8, 8);
                g2.fillOval((int) location.x - 4 + 1 + 2 + 2 + 3, (int) location.y + 116 + 2, 8, 8);

                g2.setColor(lightGrayColor);
                g2.drawOval((int) location.x - 38 - 1 + 2 + 3, (int) location.y + 116 + 2, 8, 8);
                g2.drawOval((int) location.x - 4 + 1 + 2 + 2 + 3, (int) location.y + 116 + 2, 8, 8);

//            g2.setColor(Color.BLACK); // TOO SCARY LOOK =.= HIDE THIS
//            g2.fillOval((int) location.x - 38-1+2 +2 +3 , (int) location.y + 116 + 2+2, 6, 6);
//            g2.fillOval((int) location.x - 4 + 1 +2+2 +2 +3 , (int) location.y + 116 + 2+2, 6, 6);
            }
            if (eyeMoveCount % 30 == 0) {
                if (eyeMoveLocation < 3) eyeMoveLocation++;
                else if (eyeMoveLocation == 3) eyeMoveLocation = 0;
            }

        } else if (eye == EYES_FAINT) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(5));
//            g2.fillRect((int) location.x - 38 - 4, (int) location.y + 116-5, 22, 18);
//            g2.fillRect((int) location.x - 4 - 1, (int) location.y + 116-5, 22, 18);
            g2.drawLine((int) location.x - 38 - 4, (int) location.y + 116-5, (int) location.x - 38 - 4+20, (int) location.y + 116-5+16);
            g2.drawLine((int) location.x - 38 - 4, (int) location.y + 116-5+16, (int) location.x - 38 - 4+20, (int) location.y + 116-5);

            g2.drawLine((int) location.x - 4 - 1, (int) location.y + 116-5, (int) location.x - 4 - 1+20, (int) location.y + 116-5+16);
            g2.drawLine((int) location.x - 4 - 1, (int) location.y + 116-5+16, (int) location.x - 4 - 1+20, (int) location.y + 116-5);


            g2.setStroke(new BasicStroke(2)); //reset
        }

        eyeMoveCount++;

        //drawLinepatterns
        g2.setColor(whiteColor); // ON BODY
        g2.drawLine((int) location.x-10+10-5,(int) location.y+102,(int) location.x+10+10,(int) location.y+96);
        g2.drawLine((int) location.x-5+10+5,(int) location.y+91,(int) location.x+0+10+5,(int) location.y+105);

        g2.drawLine((int) location.x-47,(int) location.y+88,(int) location.x-27,(int) location.y+100);
        g2.drawLine((int) location.x-39,(int) location.y+100,(int) location.x-20,(int) location.y+72);

        g2.drawLine((int) location.x-15,(int) location.y+50,(int) location.x-4,(int) location.y+73);
        g2.drawLine((int) location.x-24,(int) location.y+62,(int) location.x+18,(int) location.y+57);

        g2.drawLine((int) location.x-23,(int) location.y+19,(int) location.x-37,(int) location.y+47);
        g2.drawLine((int) location.x-44,(int) location.y+29,(int) location.x-12,(int) location.y+36);

        g2.setColor(Color.WHITE); // ON HANDS WHITE
        g2.drawLine((int) location.x-83+2,(int) location.y+49+2,(int) location.x-98+1,(int) location.y+72+1);
        g2.drawLine((int) location.x-70+3,(int) location.y+48+3,(int) location.x-88+1+0,(int) location.y+82+1-2);

        g2.drawLine((int) location.x+51+3,(int) location.y+49+2,(int) location.x+66+3,(int) location.y+72+1);
        g2.drawLine((int) location.x+38+3,(int) location.y+48+3,(int) location.x+56+3,(int) location.y+82+1-2);

        g2.setColor(deepGrayColor); // ON HANDS GRAY
        g2.drawLine((int) location.x-92,(int) location.y+54,(int) location.x-77,(int) location.y+78);
        g2.drawLine((int) location.x-90+3,(int) location.y+70+3,(int) location.x-78+3,(int) location.y+56+3);

        g2.drawLine((int) location.x+40,(int) location.y+63,(int) location.x+53,(int) location.y+83);
        g2.drawLine((int) location.x+50+8,(int) location.y+55,(int) location.x+62,(int) location.y+75);
        g2.drawLine((int) location.x+40,(int) location.y+80,(int) location.x+66,(int) location.y+59);

        g2.setColor(Color.WHITE); // ON TAIL WHITE
        g2.drawLine((int) location.x-54,(int) location.y-11,(int) location.x-56,(int) location.y+10);
        g2.drawLine((int) location.x-69,(int) location.y-8,(int) location.x-47,(int) location.y+1);

        g2.drawLine((int) location.x+23,(int) location.y-8,(int) location.x+38,(int) location.y-4);
        g2.drawLine((int) location.x+35,(int) location.y-16,(int) location.x+21,(int) location.y+10);

        g2.setColor(whiteColor);
        g2.drawLine((int) location.x-18,(int) location.y-20,(int) location.x-25,(int) location.y-7);
        g2.setColor(deepGrayColor);
        g2.drawLine((int) location.x-34,(int) location.y-18,(int) location.x-0,(int) location.y-8);
        g2.setColor(whiteColor);
        g2.drawLine((int) location.x-22+10,(int) location.y-14+10,(int) location.x+1,(int) location.y-15);
        g2.setColor(deepGrayColor);
        g2.drawLine((int) location.x-9,(int) location.y-20,(int) location.x-14,(int) location.y-7);

//        //draw head
//        color = new Color(13,138,199); g2.setColor(color);// lobster body color2
//        g2.fillOval((int)location.x-38,(int)location.y,42,32);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawOval((int)location.x-38,(int)location.y,42,32);
//
//        //draw left arm
//        int[] x6 = {(int)location.x-39+10, (int)location.x-81+20, (int)location.x-62+10 , (int)location.x-44
//                , (int)location.x-57+8, (int)location.x-33+5};
//        int[] y6 = {(int)location.y-8,(int)location.y+20,(int)location.y+43 ,(int)location.y+38
//                ,(int)location.y+20,(int)location.y+3};
//        color = new Color(13,152,9); g2.setColor(color);// lobster body color3
//        g2.fillPolygon(x6,y6,6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x6,y6,6);
//
//        //draw right arm
//        int[] x7 = {(int)location.x-1-5, (int)location.x+31-5+1, (int)location.x+22-5, (int)location.x+14-5
//                , (int)location.x+19-5, (int)location.x-2-5};
//        int[] y7 = {(int)location.y-8,(int)location.y+20-1,(int)location.y+43 ,(int)location.y+38
//                ,(int)location.y+20,(int)location.y+3};
//        color = new Color(13,152,9); g2.setColor(color);// lobster body color3
//        g2.fillPolygon(x7,y7,6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x7,y7,6);
//
//        //draw left hand
//        int[] x8 = {(int)location.x-42, (int)location.x-57, (int)location.x-63 , (int)location.x-60
//                , (int)location.x-44, (int)location.x-46, (int)location.x-33, (int)location.x-34};
//        int[] y8 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
//                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x8,y8,8);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x8,y8,8);
//
//        //draw right hand
//        int[] x9 = {(int)location.x+6, (int)location.x+21, (int)location.x+27 , (int)location.x+24
//                , (int)location.x+8, (int)location.x+10, (int)location.x-3, (int)location.x-2};
//        int[] y9 = {(int)location.y+33,(int)location.y+38,(int)location.y+47 ,(int)location.y+53
//                ,(int)location.y+62,(int)location.y+49-2,(int)location.y+54,(int)location.y+41-3};
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x9,y9,8);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x9,y9,8);
//
//        //draw 1st body
//        int[] x1 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y1 = {(int)location.y+4+5,(int)location.y-14+5,(int)location.y-10+5 ,(int)location.y-27+5
//                ,(int)location.y-10+5,(int)location.y-14+5 ,(int)location.y+4+5};
//        color = new Color(13,172,9); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x1,y1,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x1,y1,7);
//
//        //draw 3rd body
//        int[] x3 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y3 = {(int)location.y+4+5-25,(int)location.y-14+5-25,(int)location.y-10+5-25 ,(int)location.y-27+5-25
//                ,(int)location.y-10+5-25,(int)location.y-14+5-25 ,(int)location.y+4+5-25};
//        color = new Color(13,152,9); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x3,y3,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x3,y3,7);
//
//        //draw 5th body
//        int[] x5 = {(int)location.x-32+1, (int)location.x-28+1-5 , (int)location.x-18+1
//                , (int)location.x-9+2+5 , (int)location.x-5+2};
//        int[] y5 = {(int)location.y+4+5-40+10,(int)location.y-14+5-40-10+10 ,(int)location.y-27+5-40+20
//                ,(int)location.y-14+5-40-10+10 ,(int)location.y+4+5-40+10};
//        color = new Color(13,172,9); g2.setColor(color);// lobster body color1
//        g2.fillPolygon(x5,y5,5);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x5,y5,5);
//
//        //draw 4th body
//        int[] x4 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y4 = {(int)location.y+4+5-45,(int)location.y-14+5-45,(int)location.y-10+5-45 ,(int)location.y-27+5-45
//                ,(int)location.y-10+5-45,(int)location.y-14+5-45 ,(int)location.y+4+5-45};
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x4,y4,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x4,y4,7);
//
//        //draw 2nd body
//        int[] x2 = {(int)location.x-32+1, (int)location.x-49+1, (int)location.x-28+1 , (int)location.x-18+1
//                , (int)location.x-9+2, (int)location.x+13+1 , (int)location.x-5+2};
//        int[] y2 = {(int)location.y+4+5-15,(int)location.y-14+5-15,(int)location.y-10+5-15 ,(int)location.y-27+5-15
//                ,(int)location.y-10+5-15,(int)location.y-14+5-15 ,(int)location.y+4+5-15};
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x2,y2,7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x2,y2,7);
//
//        //draw horn
//        int[] x10 = {(int)location.x-24, (int)location.x-23, (int)location.x-13 , (int)location.x-14};
//        int[] y10 = {(int)location.y+15,(int)location.y+2,(int)location.y-1 ,(int)location.y+15};
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
//        g2.fillPolygon(x10,y10,4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawPolygon(x10,y10,4);
//        color = new Color(13,138,9); g2.setColor(color);// lobster body color2
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



    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate();

    }

    private void updateState() {
        if (state == STATE_FORWARDING) {
            if (hitCount >0 && enemyHP <=0) {  // && !(hitByStrongCount >0) removed
                if (!(enemyHP>0)) {
                    state = STATE_ESCAPING;
                    animStrategy = new piranhaMonAnimEscaping(this);
                }
//                System.out.println("hi");
            }
//            if (hitCount >0 && hitByStrongCount >0)  {
////                state = STATE_EXPLODE;
////                animStrategy = new lobsterMonAnimHurt(this);                          // REVIEW LATER
//                state = STATE_ESCAPING;
//                animStrategy = new lobsterMonAnimEscaping(this);
//            }
            if ((location.y+62+100 > 680 || hitTheTurtleHero>0)&& canHurt ==true) {     // CAN HURT <- WHEN IT IS NOT ESCAPING
                state = STATE_REACHED;
                Main.gameOver = true;
                if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                    Main.endScreen_gameOver();
                    Main.calledGameOver = true;
                }
            }
        }
        else if (state == STATE_ESCAPING) {
            if (location.y < Main.win.canvas.height - 1500) {
                state = STATE_DONE;
            }
//            System.out.println(location.y + " " + Main.win.canvas.height );

        }
        else if (state == STATE_REACHED) {
            animStrategy = new piranhaMonAnimReached(this);
        }
        else if (state == STATE_DONE) {
            super.done = true;

            notifyEvent(); // piranhaMon died
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
