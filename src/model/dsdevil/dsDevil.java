package model.dsdevil;

import controller.Main;
import controller.enemyobserver.EnemyObserver;
import controller.enemyobserver.EnemyObserverAddNew;
import controller.enemyobserver.EnemySubject;
import model.GameFigure;
import model.piranhamon.piranhaMonAnimEscaping;
import model.piranhamon.piranhaMonAnimForwarding;
import model.piranhamon.piranhaMonAnimReached;
import model.tentacle.Tentacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class dsDevil extends GameFigure implements EnemySubject {

    public static boolean DCNormalAttackOn = false;
    public static TimerTask ttDCNormalattack;
    public static int DCNormalAttackCoolDown = 0;
    public static boolean DCNormalAttackCDOn = false;
    public static int DCcount = 0;
    public static int DCdecision = 0;
    public static boolean AfterNormalAttackCDOn = false;
    public static int AfterNormalAttackCountTime = 0;


    public static int AttackMode = 0;
    public static final int ATTACK_DC = 0;
    public static final int ATTACK_DBCOMBO = 1;
    public static final int ATTACK_TENTACLES = 2;
    public static int lastAttackMode =0;


    public static boolean DBComboAttackingOn = false;
    public static boolean DBComboAttackingEnded = false;
    public static TimerTask ttDBComboattack;
    public static int DBComboAttackCoolDown = 0;
    public static boolean DBComboAttackCDOn = false;
    public static int comboModeCount = 0;

    public static Tentacle currenttentacle1;
    public static Tentacle currenttentacle2;
    public static Tentacle currenttentacle3;
    public static Tentacle currenttentacle4;

    public static boolean tentacle1AttackingOn = false;
    public static boolean tentacle2AttackingOn = false;
    public static boolean tentacle3AttackingOn = false;
    public static boolean tentacle4AttackingOn = false;

    public static boolean tentaclesAttackOn = false;
    public static boolean tentaclesAttackEnded = false;

    public static boolean tentacle1AttackingEnded = false;
    public static boolean tentacle2AttackingEnded = false;
    public static boolean tentacle3AttackingEnded = false;
    public static boolean tentacle4AttackingEnded = false;

    public static TimerTask ttTentacle1attack;
    public static TimerTask ttTentacle2attack;
    public static TimerTask ttTentacle3attack;
    public static TimerTask ttTentacle4attack;

    public static int tentacleAttackCoolDown = 0;
    public static boolean tentacleAttackCDOn = false;


    public static dsDevil currentBoss = null;

//    public static int callBigBoss = 0; //each piranha mon dies ++, if it == 2, then call the DSdevil

    public static int posingTimeCount = 0;
    public static boolean posingTimeCountOn = false;
    public static boolean posingFinished = false;

    public static int eye = 0;
    public static int EYE_NORMAL = 0;
    public static int EYE_ENTERPOSE = 1;
    public static int EYE_FLASHING_DBCOMBO1 = 2;
    public static int EYE_FLASHING_DBCOMBO2 = 3;
    public static int EYE_FLASH_ONCE=4;
    public static int EYE_FLASHING_TTCATTACK1 = 5;
    public static int EYE_FLASHING_TTCATTACK2 = 6;
    public static int EYE_FLASHING_DBCOMBO_SP = 7;
    public static int EYE_FLASHING_TTCATTACK_SP = 8;
    public static int EYE_ESCAPE = 9;


    Color violetColor = new Color (118,64,164);
    Color purpleColor = new Color (60,20,96);
    Color darkBlackColor = new Color (44,33,25);

    Color tendarkRedColor = new Color (125,38,35);
    Color tenlightGrayColor = new Color(174,170,169);


    public static int eyeFlashingTimeCount =0;
    public static boolean eyeFlashingTimeCountOn = false;
    public static boolean eyeFlashingFinished = false;

    public static int mouth = 0;
    public static int MOUTH_ENTERPOSE = 0;
    public static int MOUTH_SMALL = 1;
    public static int MOUTH_BIG =2;
    public static int mouthChangeCount =0;
//    public static int MOUTH_ESCAPE = 0; // EQUALS ENTERPOSE



    public static int UNIT_MOVE = 2; // slower than lob mon
    public static int UNIT_MOVE_ESCAPING = 2;
    public static final int STATE_ENTERING = 0;
    public static final int STATE_POSING = 1;
    public static final int STATE_NORMALATTACKING = 2;
    public static final int STATE_DBCOMBOATTACKING = 3;
    public static final int STATE_TENTACLEATTACKING = 4;

    public static final int STATE_ESCAPING = 5;
    public static final int STATE_DONE = 6;


    public static final int STATE_EXPLODE = 3;

    int size = 40;
    int width, height;
    int temptime;
    int TempTimeClock;
//    boolean movingRight = true;

    public int state;
    Color color;

    Color deepGrayColor = new Color(119,114, 113);
    Color lightGrayColor = new Color(174,170,169);
    Color whiteColor = new Color (233,230,224);
    Color mouthColor = new Color (247,96,50);

    Color darkColor = new Color (45,38,55);
    Color crownColor = new Color (131,131,175);
    Color deepRedColor = new Color (204,0,0);
    Color eyeColor = new Color (204,0,0);
    Color lightRedColor = new Color(255,77,77);
    Color darkRedColor = new Color (172,0,0);
    Color faceGrayColor = new Color (220,220,224);

    int eyeMoveCount = 0;
    int eyeMoveLocation = 0;

    dsDevilAnimStrategy animStrategy;


    ArrayList<EnemyObserver> listeners = new ArrayList<>();   // CHECK IF THIS NEED TO REVISE LATER

    int isBigBoss = 0;
    int winWalk = 0;                                         // H A N D L E   T H I S   L A T E R

    public dsDevil(int x, int y) {
        super(x, y);
        width = size;
        height = size/2;

        state = STATE_ENTERING; // initial state
        color = Color.BLUE;

        animStrategy = new dsDevilAnimEntering(this);

        enemyINDEX = 4; //ds Devil
        enemyHP = 360;

        eye = EYE_ENTERPOSE;
        mouth = MOUTH_ENTERPOSE;

        currentBoss = this;
        posingFinished = false;

        AttackMode = ATTACK_DC;

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

        // draw draft
        g2.drawRect((int)location.x-100/2, (int)location.y-100/2, 100, 100);

        // draw body oval
        g2.setColor(darkColor);
        g2.fillOval((int)location.x-380/2, (int)location.y-200-550/2+35+5, 380, 550 );
        g2.setColor(deepGrayColor);
        g2.drawOval((int)location.x-380/2, (int)location.y-200-550/2+35+5, 380, 550 );

        // draw body outside
        int[] x1 = {(int)location.x-127, (int)location.x-197, (int)location.x-152 , (int)location.x-62-5+4
                , (int)location.x+63, (int)location.x+153, (int)location.x+198, (int)location.x+128};
        int[] y1 = {(int)location.y-149+5,(int)location.y-15+5,(int)location.y+65+5 ,(int)location.y+105+5
                ,(int)location.y+105+5,(int)location.y+65+5,(int)location.y-15+5,(int)location.y-149+5};
        g2.setColor(darkColor);
        g2.fillPolygon(x1,y1,8);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x1,y1,8);

        g2.setColor(darkColor);
        g2.fillOval((int)location.x-378/2, (int)location.y-200-548/2+35+5, 378, 548 );

        // draw face oval
        g2.setColor(deepGrayColor);
        g2.drawOval((int)location.x-105/2, (int)location.y-120/2-5, 105, 120 );

        // draw crwon
        int[] x2 = {(int)location.x-3+3, (int)location.x-31+3, (int)location.x-10+3 , (int)location.x-42+3
                , (int)location.x-69+3, (int)location.x-105+3, (int)location.x-135+3, (int)location.x-83+3
                ,(int)location.x-46+3, (int)location.x+40+3, (int)location.x+77+3 , (int)location.x+129+3
                , (int)location.x+99+3, (int)location.x+63+3, (int)location.x+36+3, (int)location.x+4+3
                ,(int)location.x+25+3};
        int[] y2 = {(int)location.y-146,(int)location.y-117,(int)location.y-120 ,(int)location.y-84
                ,(int)location.y-129,(int)location.y-112,(int)location.y-50,(int)location.y-69
                ,(int)location.y-38,(int)location.y-38,(int)location.y-69 ,(int)location.y-50
                ,(int)location.y-112,(int)location.y-129,(int)location.y-84,(int)location.y-120
                ,(int)location.y-117};
        g2.setColor(crownColor);
        g2.fillPolygon(x2,y2,17);
        g2.setColor(whiteColor);
        g2.drawPolygon(x2,y2,17);

        // draw lower face
        int[] x4 = {(int)location.x-42, (int)location.x-60+5-10+5+2, (int)location.x-50+5-10+5 , (int)location.x-31+5+3
                , (int)location.x+31-5-3, (int)location.x+50-5+10-5, (int)location.x+60-5+10-5-2, (int)location.x+42};
        int[] y4 = {(int)location.y-35,(int)location.y+26-5-10-10-10-5,(int)location.y+48-5-10 ,(int)location.y+59
                ,(int)location.y+59,(int)location.y+48-5-10,(int)location.y+26-5-10-10-10-5,(int)location.y-35};
        g2.setColor(crownColor);
        g2.fillPolygon(x4,y4,8);
        g2.setColor(lightGrayColor);
        g2.drawPolygon(x4,y4,8);

        // draw upper face
        int[] x3 = {(int)location.x-40, (int)location.x-30, (int)location.x+0 , (int)location.x+30
                ,(int)location.x+40};
        int[] y3 = {(int)location.y-40,(int)location.y-75,(int)location.y-64 ,(int)location.y-75
                ,(int)location.y-40};
        g2.setColor(faceGrayColor);
        g2.fillPolygon(x3,y3,5);
        g2.setColor(deepGrayColor);
        g2.drawPolygon(x3,y3,5);

        g2.setColor(faceGrayColor); // cover face
        g2.fillOval((int)location.x-103/2, (int)location.y-118/2-5, 103, 118 );

        // draw crystal
        g2.setStroke(new BasicStroke(2));
        //123D
        int[] x16 = {(int) location.x + 0, (int) location.x - 10, (int) location.x - 4};
        int[] y16 = {(int) location.y - 20-75, (int) location.y - 12-75, (int) location.y - 10-75};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x16,y16,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x16,y16,3);
//
//        //143L
        int[] x15 = {(int) location.x + 0, (int) location.x + 4, (int) location.x - 4};
        int[] y15 = {(int) location.y - 20-75, (int) location.y - 10-75, (int) location.y - 10-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x15,y15,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x15,y15,3);


//
//        //145D
        int[] x13 = {(int) location.x + 0, (int) location.x + 4, (int) location.x + 10};
        int[] y13 = {(int) location.y - 20-75, (int) location.y - 10-75, (int) location.y - 12-75};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x13,y13,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x13,y13,3);



//
//        //23 1312 L
        int[] x14 = {(int) location.x - 10, (int) location.x - 4, (int) location.x - 4, (int) location.x - 10};
        int[] y14 = {(int) location.y - 12-75, (int) location.y - 10-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x14,y14,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x14,y14,4);



//
//        //43 1314 D
        int[] x5 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 4, (int) location.x + 4};
        int[] y5 = {(int) location.y - 10-75, (int) location.y - 10-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x5,y5,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x5,y5,4);



//
//        //45 1514 L
        int[] x6 = {(int) location.x + 4, (int) location.x + 10, (int) location.x + 10, (int) location.x + 4};
        int[] y6 = {(int) location.y - 10-75, (int) location.y - 12-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x6,y6,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x6,y6,4);


//
//        //67 1312 L
        int[] x7 = {(int) location.x - 10, (int) location.x - 4, (int) location.x - 4, (int) location.x - 10};
        int[] y7 = {(int) location.y + 12-75, (int) location.y + 10-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x7,y7,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x7,y7,4);





//
//        //87 1314 D
        int[] x8 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 4, (int) location.x + 4};
        int[] y8 = {(int) location.y + 10-75, (int) location.y + 10-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x8,y8,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x8,y8,4);





//
//        //89 1514 L
        int[] x9 = {(int) location.x + 4, (int) location.x + 10, (int) location.x + 10, (int) location.x + 4};
        int[] y9 = {(int) location.y + 10-75, (int) location.y + 12-75, (int) location.y + 0-75, (int) location.y + 0-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x9,y9,4);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x9,y9,4);




//
//        //67 10 D ->L
        int[] x10 = {(int) location.x - 10, (int) location.x - 4, (int) location.x - 0};
        int[] y10 = {(int) location.y + 12-75, (int) location.y + 10-75, (int) location.y + 20-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x10,y10,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x10,y10,3);


//
//        //87 10 L ->D
        int[] x11 = {(int) location.x + 4, (int) location.x - 4, (int) location.x - 0};
        int[] y11 = {(int) location.y + 10-75, (int) location.y + 10-75, (int) location.y + 20-75};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x11,y11,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x11,y11,3);


//
//        //89 10 D ->L
        int[] x12 = {(int) location.x + 4, (int) location.x + 10, (int) location.x + 0};
        int[] y12 = {(int) location.y + 10-75, (int) location.y + 12-75, (int) location.y + 20-75};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x12,y12,3);
        g2.setColor(darkRedColor);
        g2.drawPolygon(x12,y12,3);

        g2.setColor(faceGrayColor); // cover face
        g2.fillOval((int)location.x-4, (int)location.y-55, 8, 8 ); // cover face

        // 23 76 L COVER
        int[] x17 = {(int) location.x - 10+1, (int) location.x - 4-1, (int) location.x - 4-2, (int) location.x - 10+1};
        int[] y17 = {(int) location.y - 12+1-75, (int) location.y - 10-75+1, (int) location.y + 10-75+2, (int) location.y + 12-75+1};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x17,y17,4);

        // 43 78 D COVER
        int[] x18 = {(int) location.x + 4-1, (int) location.x - 4+1, (int) location.x - 4+1, (int) location.x + 4-1};
        int[] y18 = {(int) location.y - 10-75+1, (int) location.y - 10-75+1, (int) location.y + 10-75+1, (int) location.y + 10-75+1};
        g2.setColor(deepRedColor);
        g2.fillPolygon(x18,y18,4);


        // 45 98 L COVER
        int[] x19 = {(int) location.x + 4+1, (int) location.x + 10-1, (int) location.x + 10-1, (int) location.x + 4+1};
        int[] y19 = {(int) location.y - 10-75+1, (int) location.y - 12-75+1, (int) location.y + 12-75+1, (int) location.y + 10-75+1};
        g2.setColor(lightRedColor);
        g2.fillPolygon(x19,y19,4);

        g2.setStroke(new BasicStroke(2));

        // eye part
        // draw left eye shadow  -+ move left down
        int[] x20 = {(int)location.x-34-5-3, (int)location.x-26-5-3, (int)location.x-12-3 , (int)location.x-4-3};
        int[] y20 = {(int)location.y-46+10+4,(int)location.y-27+10+2+4,(int)location.y-28+10+2+4 ,(int)location.y-39+10+4};
        g2.setColor(Color.BLACK);
        g2.fillPolygon(x20,y20,4);

        // draw right eye shadow ++ move right down
        int[] x21 = {(int)location.x+34+5+3, (int)location.x+26+5+3, (int)location.x+12+3 , (int)location.x+4+3};
        int[] y21 = {(int)location.y-46+10+4,(int)location.y-27+10+2+4,(int)location.y-28+10+2+4 ,(int)location.y-39+10+4};
        g2.setColor(Color.BLACK);
        g2.fillPolygon(x21,y21,4);

        // eye flash part
        if (eye == EYE_ENTERPOSE) {
            eyeColor = deepGrayColor;
        }
        else if (eye == EYE_NORMAL) {
            eyeColor = deepRedColor;
        }
        else if (eye == EYE_FLASH_ONCE) {
            eyeColor = lightRedColor;
        }
        else if (eye == EYE_FLASHING_DBCOMBO1){
            eyeColor = darkBlackColor;
        }
        else if (eye == EYE_FLASHING_DBCOMBO2){
            eyeColor = purpleColor;
        }
        else if (eye == EYE_FLASHING_DBCOMBO_SP){
            eyeColor = violetColor;
        }

        if (eye == EYE_FLASHING_TTCATTACK1){
            eyeColor = crownColor;
        }
        else if (eye == EYE_FLASHING_TTCATTACK2){
            eyeColor = tendarkRedColor;
        }
        else if (eye == EYE_FLASHING_TTCATTACK_SP) {
            eyeColor = tenlightGrayColor;
        }

        if (eye == EYE_ESCAPE) {
            eyeColor = new Color(40,40,40);
        }

        // draw left eye
        int[] x22 = {(int)location.x-34-5, (int)location.x-26-5, (int)location.x-12 , (int)location.x-4};
        int[] y22 = {(int)location.y-46+10,(int)location.y-27+10+2,(int)location.y-28+10+2 ,(int)location.y-39+10};
        g2.setColor(eyeColor);
        g2.fillPolygon(x22,y22,4);

        // draw right eye
        int[] x23 = {(int)location.x+34+5, (int)location.x+26+5, (int)location.x+12 , (int)location.x+4};
        int[] y23 = {(int)location.y-46+10,(int)location.y-27+10+2,(int)location.y-28+10+2 ,(int)location.y-39+10};
        g2.setColor(eyeColor);
        g2.fillPolygon(x23,y23,4);



        // mouth part
        if (mouth == MOUTH_ENTERPOSE){
            g2.setColor(Color.BLACK);
            g2.fillRect((int) location.x - 22, (int) location.y +22,44, 5);
            g2.fillRect((int) location.x - 22, (int) location.y +14,44, 5);
            g2.setColor(deepGrayColor);
            g2.fillRect((int) location.x - 22, (int) location.y +19,44, 5);
            g2.fillRect((int) location.x - 16, (int) location.y +17,32, 5);
        }
        else if (mouth == MOUTH_SMALL) {
            // draw small mouth
            int[] x24 = {(int) location.x - 20, (int) location.x - 30, (int) location.x - 19, (int) location.x + 19
                    , (int) location.x + 30, (int) location.x + 20};
            int[] y24 = {(int) location.y + 7, (int) location.y + 18, (int) location.y + 29, (int) location.y + 29
                    , (int) location.y + 18, (int) location.y + 7};
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x24, y24, 6);

            // draw small teeth
            //upper
            int[] x25 = {(int) location.x - 20, (int) location.x - 15 + 2, (int) location.x - 8};
            int[] y25 = {(int) location.y + 7, (int) location.y + 19, (int) location.y + 7};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x25, y25, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x25, y25, 3);

            int[] x26 = {(int) location.x - 8, (int) location.x - 5 + 2, (int) location.x - 0};
            int[] y26 = {(int) location.y + 7, (int) location.y + 18, (int) location.y + 7};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x26, y26, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x26, y26, 3);

            int[] x27 = {(int) location.x - 0, (int) location.x + 4, (int) location.x + 11};
            int[] y27 = {(int) location.y + 7, (int) location.y + 19, (int) location.y + 7};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x27, y27, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x27, y27, 3);

            int[] x28 = {(int) location.x + 11, (int) location.x + 13, (int) location.x + 20};
            int[] y28 = {(int) location.y + 7, (int) location.y + 19, (int) location.y + 7};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x28, y28, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x28, y28, 3);

            //lower
            int[] x29 = {(int) location.x - 20, (int) location.x - 15, (int) location.x - 10};
            int[] y29 = {(int) location.y + 29, (int) location.y + 20, (int) location.y + 29};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x29, y29, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x29, y29, 3);

            int[] x30 = {(int) location.x - 10, (int) location.x - 5 - 3, (int) location.x - 0};
            int[] y30 = {(int) location.y + 29, (int) location.y + 18, (int) location.y + 29};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x30, y30, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x30, y30, 3);

            int[] x31 = {(int) location.x - 0, (int) location.x + 5 - 2, (int) location.x + 10};
            int[] y31 = {(int) location.y + 29, (int) location.y + 19, (int) location.y + 29};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x31, y31, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x31, y31, 3);

            int[] x32 = {(int) location.x + 10, (int) location.x + 15 + 1 + 1, (int) location.x + 20};
            int[] y32 = {(int) location.y + 29, (int) location.y + 20 - 2 - 1, (int) location.y + 29};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x32, y32, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x32, y32, 3);

            //side
            int[] x33 = {(int) location.x - 20, (int) location.x - 18, (int) location.x - 27};
            int[] y33 = {(int) location.y + 7, (int) location.y + 23, (int) location.y + 15};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x33, y33, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x33, y33, 3);

            int[] x34 = {(int) location.x + 20, (int) location.x + 19 + 2, (int) location.x + 27};
            int[] y34 = {(int) location.y + 7, (int) location.y + 23 - 1, (int) location.y + 15};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x34, y34, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x34, y34, 3);
            //small mouth teeth end
        }
        else if (mouth == MOUTH_BIG) {
            // draw big mouth
            int[] x34 = {(int) location.x - 20, (int) location.x - 30, (int) location.x - 19, (int) location.x + 19
                    , (int) location.x + 30, (int) location.x + 20};
            int[] y34 = {(int) location.y + 0, (int) location.y + 18, (int) location.y + 40, (int) location.y + 40
                    , (int) location.y + 18, (int) location.y + 0};
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x34, y34, 6);

            // draw big teeth
            //upper
            int[] x35 = {(int) location.x - 20, (int) location.x - 15 + 2, (int) location.x - 8};
            int[] y35 = {(int) location.y + 0, (int) location.y + 12, (int) location.y + 0};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x35, y35, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x35, y35, 3);

            int[] x36 = {(int) location.x - 8, (int) location.x - 5 + 2, (int) location.x - 0};
            int[] y36 = {(int) location.y + 0, (int) location.y + 11, (int) location.y + 0};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x36, y36, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x36, y36, 3);

            int[] x37 = {(int) location.x - 0, (int) location.x + 4, (int) location.x + 11};
            int[] y37 = {(int) location.y + 0, (int) location.y + 12, (int) location.y + 0};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x37, y37, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x37, y37, 3);

            int[] x38 = {(int) location.x + 11, (int) location.x + 13, (int) location.x + 20};
            int[] y38 = {(int) location.y + 0, (int) location.y + 12, (int) location.y + 0};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x38, y38, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x38, y38, 3);

            //lower
            int[] x39 = {(int) location.x - 20, (int) location.x - 15, (int) location.x - 10};
            int[] y39 = {(int) location.y + 40, (int) location.y + 31, (int) location.y + 40};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x39, y39, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x39, y39, 3);

            int[] x40 = {(int) location.x - 10, (int) location.x - 5 - 3, (int) location.x - 0};
            int[] y40 = {(int) location.y + 40, (int) location.y + 29, (int) location.y + 40};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x40, y40, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x40, y40, 3);

            int[] x41 = {(int) location.x - 0, (int) location.x + 5 - 2, (int) location.x + 10};
            int[] y41 = {(int) location.y + 40, (int) location.y + 30, (int) location.y + 40};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x41, y41, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x41, y41, 3);

            int[] x42 = {(int) location.x + 10, (int) location.x + 15 + 1 + 1, (int) location.x + 20};
            int[] y42 = {(int) location.y + 40, (int) location.y + 28, (int) location.y + 40};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x42, y42, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x42, y42, 3);

            //side
            int[] x43 = {(int) location.x - 20, (int) location.x - 18, (int) location.x - 27};
            int[] y43 = {(int) location.y + 0, (int) location.y + 16, (int) location.y + 8};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x43, y43, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x43, y43, 3);

            int[] x44 = {(int) location.x + 20, (int) location.x + 19 + 2, (int) location.x + 27};
            int[] y44 = {(int) location.y + 0, (int) location.y + 15, (int) location.y + 8};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(x44, y44, 3);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x44, y44, 3);
            //big mouth teeth end
        }

        // inside this enemy, count var (reset not exceed int bounds)
        if (mouthChangeCount<10000) mouthChangeCount ++;
        else mouthChangeCount = 0;


    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate();

    }

    private void updateState() {
        if (state == STATE_ENTERING) {
            if (location.y >=150) {
                state = STATE_POSING;
                animStrategy = new dsDevilAnimPosing(this);

            }

        } else if (state == STATE_POSING) {
            if (posingTimeCount == 95) {
                state = STATE_NORMALATTACKING;
                animStrategy = new dsDevilAnimNormalAttacking(this);
                posingTimeCount = 0;
                posingFinished = true;

                // when change into normal attack mode, generate attack decision
                Random rand = new Random();
                DCcount = 0; // reset dark crystal count
                DCdecision = rand.nextInt(3)+1; // 1~4 times
            }
        }
        else if (state == STATE_NORMALATTACKING){

            Random rand = new Random();
            if(DCcount == DCdecision) { // finished DC Attack
                dsDevil.DCNormalAttackOn = false;

                if (lastAttackMode == 0) {
                    AttackMode = rand.nextInt(2) + 1;} //1-2
                else if (lastAttackMode ==1){
                    AttackMode = 2;
                }
                else if (lastAttackMode ==2){
                    AttackMode = 1;
                }
//                AfterNormalAttackCountTime = 0; // reset here for sure... // cannot do this... every update will be 0...

                if (dsDevil.AfterNormalAttackCountTime>100) dsDevil.AfterNormalAttackCountTime = 0; // reset to play safe...

                if (dsDevil.AfterNormalAttackCountTime==0) dsDevil.AfterNormalAttackCDOn = true; // count80, need condition, otherwise always on...

                System.out.println("LINE669 AttackMode "+ AttackMode + " AfterNormalAttackCount " + AfterNormalAttackCountTime);
                if (AfterNormalAttackCountTime==80) {
                    System.out.println("LINE671 AttackMode "+ AttackMode);
                    if (AttackMode == 1) {
                        state = STATE_DBCOMBOATTACKING;
                        animStrategy = new dsDevilAnimDBComboAttacking(this);
                        DBComboAttackingOn = true;
                        eyeFlashingTimeCountOn = true;

                        DBComboAttackingEnded = false; // reset

                        System.out.println("LINE679 AttackMode "+ AttackMode);

                        dsDevilAnimDBComboAttacking.DBCombostart = true;
                        dsDevilAnimDBComboAttacking.firstball = 0;
                        dsDevilAnimDBComboAttacking.secondball = 0;
                        dsDevilAnimDBComboAttacking.thirdball = 0;

                        dsDevilAnimDBComboAttacking.DBAddCount = 0;
                        dsDevilAnimDBComboAttacking.DBAddDelayCountTime = 0;
                        dsDevilAnimDBComboAttacking.shotBall1 = false; // RESET BEFORE START
                        dsDevilAnimDBComboAttacking.shotBall2 = false;
                        dsDevilAnimDBComboAttacking.shotBall3 = false;

                        DCcount = 0; // reset dark crystal count
                        DCdecision = 0; // stop the condition in Main stop dark ball

                        AfterNormalAttackCountTime = 0; //reset cd time
                    }
                    if (AttackMode == 2) {
                        state = STATE_TENTACLEATTACKING;
                        animStrategy = new dsDevilAnimTentacleAttacking(this);
                        tentaclesAttackOn = true;

                        eyeFlashingTimeCountOn = true;

                        System.out.println("LINE700 AttackMode "+ AttackMode);

                        tentacle1AttackingEnded = false;
                        tentacle2AttackingEnded = false;
                        tentacle3AttackingEnded = false;
                        tentacle4AttackingEnded = false;

                        DCcount = 0; // reset dark crystal count
                        DCdecision = 0; // stop the condition in Main stop dark ball

                        AfterNormalAttackCountTime = 0; //reset cd time
                    }
                    lastAttackMode = AttackMode;
                    System.out.println("LINE711 LAST ATTACK MODE "+ lastAttackMode);

                    AfterNormalAttackCountTime = 0; //reset cd time // not work here, sometimes out bounds, so make sure, put inside
                }
            }


            if (hitCount >0 && enemyHP <=0) {  // && !(hitByStrongCount >0) removed
                if (!(enemyHP>0)) {
                    state = STATE_ESCAPING;
                    animStrategy = new dsDevilAnimEscaping(this);
                }
            }

            if (hitTheTurtleHero>0 && canHurt ==true) {     // CAN HURT <- WHEN IT IS NOT ESCAPING
//                state = STATE_POSING;       // C H A N G E  I T  L A T E R ...................................
                Main.gameOver = true;
                if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                    Main.endScreen_gameOver();
                    Main.calledGameOver = true;
                }
            }
        }
        else if (state == STATE_DBCOMBOATTACKING){
            int temptime = Main.TimeClock;   // avoid multiple add
            if (TempTimeClock == -1) {
                TempTimeClock = temptime;
            } else if (TempTimeClock == temptime){
                TempTimeClock = -1;
            } else {
                TempTimeClock = Main.TimeClock;
            }
            if (DBComboAttackingEnded == true && comboModeCount <2 && TempTimeClock!=-1) {
                comboModeCount++;
                DBComboAttackingOn = true;
                state = STATE_DBCOMBOATTACKING;
                animStrategy = new dsDevilAnimDBComboAttacking(this);


                DBComboAttackingEnded = false; // reset

                dsDevilAnimDBComboAttacking.DBCombostart = true;
                dsDevilAnimDBComboAttacking.firstball = 0;
                dsDevilAnimDBComboAttacking.secondball = 0;
                dsDevilAnimDBComboAttacking.thirdball = 0;

                dsDevilAnimDBComboAttacking.DBAddCount =0;
                dsDevilAnimDBComboAttacking.DBAddDelayCountTime = 0;
                dsDevilAnimDBComboAttacking.shotBall1 = false; // RESET BEFORE START
                dsDevilAnimDBComboAttacking.shotBall2 = false;
                dsDevilAnimDBComboAttacking.shotBall3 = false;

                DCcount = 0; // reset dark crystal count
                DCdecision = 0; // stop the condition in Main stop dark ball
            }
            if (DBComboAttackingEnded == true && comboModeCount ==2){
                state = STATE_NORMALATTACKING;
                animStrategy = new dsDevilAnimNormalAttacking(this);
                // when change into normal attack mode, generate attack decision
                Random rand = new Random();
                DCcount = 0; // reset dark crystal count
                DCdecision = rand.nextInt(4)+1; // 1~4 times
                DBComboAttackingEnded = false; // reset

                comboModeCount = 0; // reset 3 times 3 combos
            }


            if (hitCount >0 && enemyHP <=0) {  // && !(hitByStrongCount >0) removed
                if (!(enemyHP>0)) {
                    state = STATE_ESCAPING;
                    animStrategy = new dsDevilAnimEscaping(this);
                }
            }

            if (hitTheTurtleHero>0 && canHurt ==true) {     // CAN HURT <- WHEN IT IS NOT ESCAPING
//                state = STATE_POSING;       // C H A N G E  I T  L A T E R ...................................
                Main.gameOver = true;
                if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                    Main.endScreen_gameOver();
                    Main.calledGameOver = true;
                }
            }
        }

        else if (state == STATE_TENTACLEATTACKING){
            if (tentaclesAttackEnded == true){
                state = STATE_NORMALATTACKING;
                animStrategy = new dsDevilAnimNormalAttacking(this);
                // when change into normal attack mode, generate attack decision
                Random rand = new Random();
                DCcount = 0; // reset dark crystal count
                DCdecision = rand.nextInt(4)+1; // 1~4 times
                tentaclesAttackEnded = false; // reset
                dsDevilAnimTentacleAttacking.chosen1 = false;
                dsDevilAnimTentacleAttacking.chosen2 = false;
                dsDevilAnimTentacleAttacking.chosen3 = false;
                dsDevilAnimTentacleAttacking.chosen4 = false;
            }


            if (hitCount >0 && enemyHP <=0) {  // && !(hitByStrongCount >0) removed
                if (!(enemyHP>0)) {
                    state = STATE_ESCAPING;
                    animStrategy = new dsDevilAnimEscaping(this);
                }
            }

            if (hitTheTurtleHero>0 && canHurt ==true) {     // CAN HURT <- WHEN IT IS NOT ESCAPING
//                state = STATE_POSING;       // C H A N G E  I T  L A T E R ...................................
                Main.gameOver = true;
                if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                    Main.endScreen_gameOver();
                    Main.calledGameOver = true;
                }
            }

        }

        else if (state == STATE_ESCAPING) {
            if (location.y < Main.win.canvas.height - 100) {
                if (EnemyObserverAddNew.EnemydiedCount == 6) {temptime = Main.TimeClock;}
                if (temptime == Main.TimeClock) {
                    notifyEvent();   // AS IF IT HAS LEFT THE SCREEN AND IS DIED ALREADY ...
                }
            }

            if (location.y < Main.win.canvas.height - 1500) {
                state = STATE_DONE;
            }
//            System.out.println(location.y + " " + Main.win.canvas.height );

        }
        else if (state == STATE_POSING) {
            animStrategy = new dsDevilAnimPosing(this);
        }
        else if (state == STATE_DONE) {
            super.done = true;

//            notifyEvent(); // TOO SLOW, MOVE UP TO ESCAPING
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

        return 120+75;
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
