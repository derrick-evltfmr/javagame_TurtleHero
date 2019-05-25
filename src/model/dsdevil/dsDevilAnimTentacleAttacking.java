package model.dsdevil;

import controller.InputEvent;
import controller.Main;
import controller.enemyobserver.EnemyCreateEvent;

import java.util.Random;

public class dsDevilAnimTentacleAttacking implements dsDevilAnimStrategy {

    dsDevil context;
    int decision;
    boolean decisionOK;
    public static boolean chosen1 = false;
    public static boolean chosen2 = false;
    public static boolean chosen3 = false;
    public static boolean chosen4 = false;

    public dsDevilAnimTentacleAttacking(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
        // eye mouth part
        if (!context.eyeFlashingTimeCountOn){
            context.eye = context.EYE_NORMAL;
        }else {
            if (context.eyeFlashingTimeCount >= 3) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 5) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 7) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 9) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 11) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 14) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 17) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 19) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 21) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 24) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 27) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 29) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 31) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 34) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 37) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 39) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 41) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 44) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 47) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 49) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 51) context.eye = context.EYE_FLASHING_TTCATTACK1;
            if (context.eyeFlashingTimeCount >= 54) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
            if (context.eyeFlashingTimeCount >= 57) context.eye = context.EYE_FLASHING_TTCATTACK2;
            if (context.eyeFlashingTimeCount >= 59) context.eye = context.EYE_FLASHING_TTCATTACK_SP;
        }




        if (context.mouthChangeCount %40 ==0 ) {
            if (context.mouth == context.MOUTH_SMALL ) context.mouth = context.MOUTH_BIG;
            else if (context.mouth == context.MOUTH_BIG) context.mouth = context.MOUTH_SMALL;
        }


        if (context.tentacle1AttackingEnded && context.tentacle2AttackingEnded
                && context.tentacle3AttackingEnded && context.tentacle4AttackingEnded) {
            context.tentaclesAttackEnded = true;
        }

//        System.out.println("1 On " +context.tentacle1AttackingOn + "Eneded " +context.tentacle1AttackingEnded);
//        System.out.println("2 On " +context.tentacle2AttackingOn + "Eneded " +context.tentacle2AttackingEnded);
//        System.out.println("3 On " +context.tentacle3AttackingOn + "Eneded " +context.tentacle3AttackingEnded);
//        System.out.println("4 On " +context.tentacle4AttackingOn + "Eneded " +context.tentacle4AttackingEnded);
        if (context.tentaclesAttackOn == true && context.tentaclesAttackEnded == false) {
            Random rand = new Random();
            decisionOK = false;
            decision = rand.nextInt(4)+1; //1-4
            while ((chosen1 == true && decision ==1) || (chosen2 == true && decision ==2)
                    || (chosen3 == true && decision ==3) || (chosen4 == true && decision ==4)){
                decision = rand.nextInt(4)+1;
                if (chosen1 && chosen2 && chosen3 && chosen4) {
                    context.tentaclesAttackEnded=true;
                    break;
                }
            }
            if (decision ==1) {
                decisionOK = false;
                if (context.currenttentacle1 == null  && chosen1 == false&&context.tentacle1AttackingEnded == false){
                    decisionOK = true;
                }
                if (decisionOK == true){
                    // attack part
                    context.tentacle1AttackingOn = true;
                    InputEvent tentacle1event = new InputEvent();
                    tentacle1event.event = new EnemyCreateEvent("Tentacle",
                            (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                    tentacle1event.type = InputEvent.DSDEVIL_NORMAL_DC; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(tentacle1event);

                }
            } else if (decision ==2) {
                decisionOK = false;
                if (context.currenttentacle2 == null &&chosen2 == false && context.tentacle2AttackingEnded == false){
                    decisionOK = true;
                }
                if (decisionOK == true){
                    // attack part
                    context.tentacle2AttackingOn = true;
                    InputEvent tentacle2event = new InputEvent();
                    tentacle2event.event = new EnemyCreateEvent("Tentacle",
                            (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                    tentacle2event.type = InputEvent.DSDEVIL_NORMAL_DC; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(tentacle2event);

                }
            } else if (decision ==3) {
                decisionOK = false;
                if (context.currenttentacle3 == null  &&chosen3 == false&& context.tentacle3AttackingEnded == false){
                    decisionOK = true;
                }
                if (decisionOK == true){
                    // attack part
                    context.tentacle3AttackingOn = true;
                    InputEvent tentacle3event = new InputEvent();
                    tentacle3event.event = new EnemyCreateEvent("Tentacle",
                            (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                    tentacle3event.type = InputEvent.DSDEVIL_NORMAL_DC; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(tentacle3event);

                }
            } else if (decision ==4) {
                decisionOK = false;
                if (context.currenttentacle4 == null &&chosen4 == false && context.tentacle4AttackingEnded == false){
                    decisionOK = true;
                }
                if (decisionOK == true){
                    // attack part
                    context.tentacle4AttackingOn = true;
                    InputEvent tentacle4event = new InputEvent();
                    tentacle4event.event = new EnemyCreateEvent("Tentacle",
                            (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                    tentacle4event.type = InputEvent.DSDEVIL_NORMAL_DC; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(tentacle4event);

                }
            }

        }

    }
}
