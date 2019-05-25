package model.dsdevil;

import controller.InputEvent;
import controller.Main;
import controller.enemyobserver.EnemyCreateEvent;

import java.util.Random;

public class dsDevilAnimDBComboAttacking implements dsDevilAnimStrategy {

    dsDevil context;

    public static boolean DBCombostart = true; //default true

    public static boolean DBAddDelayCountOn = false;
    public static int DBAddDelayCountTime = 0;
    public static int DBAddCount = 0;
    public static boolean DBAddFinished = false;

    public static boolean AfterDBComboCountOn = false;
    public static int AfterDBComboCountTime = 0;

    public static int firstball = 0;
    public static int secondball = 0;
    public static int thirdball = 0;

    public static boolean shotBall1 = false;
    public static boolean shotBall2 = false;
    public static boolean shotBall3 = false;


    public dsDevilAnimDBComboAttacking(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
        // eye mouth part
        context.eye = context.EYE_NORMAL;

        //eye flash effect // be careful the strategy will overwrite the render
        if (!context.eyeFlashingTimeCountOn){
            context.eye = context.EYE_NORMAL;
        }else {
            if (context.eyeFlashingTimeCount >= 3) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 5) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 7) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 9) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 11) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 14) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 17) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 19) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 21) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 24) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 27) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 29) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 31) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 34) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 37) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 39) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 41) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 44) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 47) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 49) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 51) context.eye = context.EYE_FLASHING_DBCOMBO1;
            if (context.eyeFlashingTimeCount >= 54) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
            if (context.eyeFlashingTimeCount >= 57) context.eye = context.EYE_FLASHING_DBCOMBO2;
            if (context.eyeFlashingTimeCount >= 59) context.eye = context.EYE_FLASHING_DBCOMBO_SP;
        }
        
        
        
        
        
        
        if (context.mouthChangeCount % 40 == 0) {
            if (context.mouth == context.MOUTH_SMALL) context.mouth = context.MOUTH_BIG;
            else if (context.mouth == context.MOUTH_BIG) context.mouth = context.MOUTH_SMALL;
        }

        // set order part
        Random temprand = new Random();
        if (firstball == 0 || secondball == 0 || thirdball == 0) {
            firstball = temprand.nextInt(3) + 1; //1,2,3
            secondball = temprand.nextInt(3) + 1; //1,2,3
            thirdball = temprand.nextInt(3) + 1; //1,2,3

            while ((firstball == secondball || secondball == thirdball || thirdball == firstball)) { // (any one of them true then run)
                firstball = temprand.nextInt(3) + 1; //1,2,3
                while (true) {
                    secondball = temprand.nextInt(3) + 1;
                    if (secondball != firstball) break;
                }
                while (true) {
                    thirdball = temprand.nextInt(3) + 1;
                    if (thirdball != firstball && thirdball != secondball) break;
                }
            }
//            System.out.println("first " + firstball + " second " + secondball + " third " + thirdball);
        }


        if (DBCombostart == true) {
            if (DBAddDelayCountTime == 0 && DBAddCount == 0) DBAddDelayCountOn = true; // first time
            int tempDBAddCount = DBAddCount;
            DBAddCount = (int) (DBAddDelayCountTime / 30.0);  // 360 /100 ok but shoot too slow
            if (DBAddCount > tempDBAddCount) {
                InputEvent dbcomboevent = new InputEvent();
                dbcomboevent.event = new EnemyCreateEvent("DarkEnergyBall",
                        (int) context.location.x, (int) context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                dbcomboevent.type = InputEvent.DSDEVIL_COMBO_DB; // let InputEvent handle it...
                Main.playerInputEventQueue.queue.add(dbcomboevent);
//                System.out.println("LINE57 " + DBAddCount);
//                System.out.println("shot1 " + shotBall1 + " shot2 " + shotBall2 + " shot3 " + shotBall3);
            }
//            System.out.println("DBADDCOUNT "+DBAddCount + " ")
            if (DBAddCount == 3 && DBAddDelayCountTime == 100) { // FINISHED THE 3 ROUND ALREADY 100 is the max delay time
                if (shotBall1 && shotBall2 && shotBall3) {
                    DBAddFinished = true;

                    AfterDBComboCountOn = true; // start to count delay before ending this state

//                    System.out.println("LINE63 " + DBAddCount);
                } else {
                    DBAddCount++;
                    InputEvent dbcomboevent = new InputEvent();
                    dbcomboevent.event = new EnemyCreateEvent("DarkEnergyBall",
                            (int) context.location.x, (int) context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                    dbcomboevent.type = InputEvent.DSDEVIL_COMBO_DB; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(dbcomboevent);
//                    System.out.println("LINE72 " + DBAddCount);
                }


//            if (DBAddDelayCountTime % 90 == 0) {
//                    if (DBAddCount < 3) { // handle DB Add Count 1,2,3    // FAIL.......
//                        DBAddCount++;
////                        DBAddDelayCountTime = 0;   //FAIL
////                        DBAddDelayCountOn = true;  //FAIL
//                        System.out.println("LINE47 " + DBAddCount);
//                    }

                //ADD event 3times part
//                    if (DBAddCount <=3) {
//                        InputEvent dbcomboevent = new InputEvent();
//                        dbcomboevent.event = new EnemyCreateEvent("DarkEnergyBall",
//                                (int) context.location.x, (int) context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
//                        dbcomboevent.type = InputEvent.DSDEVIL_COMBO_DB; // let InputEvent handle it...
//                        Main.playerInputEventQueue.queue.add(dbcomboevent);
//                        System.out.println("LINE57 " + DBAddCount);
//                    }
                // END MAIN PART


                if (DBAddFinished == true &&
                        ((dsDevil.comboModeCount<2 && AfterDBComboCountTime ==60) ||
                        (dsDevil.comboModeCount==2 && AfterDBComboCountTime ==180)) ) { // handle after DB Add Count3
                    DBCombostart = false;
                    DBAddCount = 0;
                    DBAddDelayCountTime = 0;
                    DBAddDelayCountOn = false;

                    AfterDBComboCountTime =0;
//                    System.out.println("LINE80 " + DBAddCount);
                    context.DBComboAttackingEnded = true; // end this state
                }

//                }


//                if (dsDevil.DCcount < dsDevil.DCdecision)
//                    dsDevil.DBComboAttackingOn = true; // if not control it, will set it millions times even it stops= =


            }


//        context.DCcount = context.DCdecision; // for testing...
//
//
//
//
//
//        if (AfterDBComboCountTime >120) {context.DBComboAttackingEnded = true;}
        }
    }
}
