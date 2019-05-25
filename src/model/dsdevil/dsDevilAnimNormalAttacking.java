package model.dsdevil;

import controller.InputEvent;
import controller.Main;
import controller.enemyobserver.EnemyCreateEvent;

public class dsDevilAnimNormalAttacking implements dsDevilAnimStrategy {

    dsDevil context;

    public dsDevilAnimNormalAttacking(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
        // eye mouth part

        if (!context.AfterNormalAttackCDOn)context.eye = context.EYE_NORMAL;

        if (context.mouthChangeCount %40 ==0 ) {
            if (context.mouth == context.MOUTH_SMALL ) context.mouth = context.MOUTH_BIG;
            else if (context.mouth == context.MOUTH_BIG) context.mouth = context.MOUTH_SMALL;
        }

        // attack part
        if (dsDevil.currenttentacle1 == null && dsDevil.currenttentacle2 == null
                && dsDevil.currenttentacle3 ==null & dsDevil.currenttentacle4 ==null ) {

            if (dsDevil.DCcount < dsDevil.DCdecision)
                dsDevil.DCNormalAttackOn = true; // if not control it, will set it millions times even it stops= =
            InputEvent dcnormalevent = new InputEvent();
            dcnormalevent.event = new EnemyCreateEvent("DarkCrystal",
                    (int) context.location.x, (int) context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
            dcnormalevent.type = InputEvent.DSDEVIL_NORMAL_DC; // let InputEvent handle it...
            Main.playerInputEventQueue.queue.add(dcnormalevent);

        }






    }
}
