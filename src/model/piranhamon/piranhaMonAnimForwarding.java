package model.piranhamon;

import controller.InputEvent;
import controller.Main;
import controller.enemyobserver.EnemyCreateEvent;
import model.compressediceberg.CompressedIceBerg;
import model.lobstermon.lobsterMon;
import model.lobstermon.lobsterMonAnimStrategy;

import static controller.Main.gameData;

public class piranhaMonAnimForwarding implements piranhaMonAnimStrategy {

    piranhaMon context;

    public piranhaMonAnimForwarding(piranhaMon context) {
        this.context = context;
    }

    @Override
    public void animate() {
//        if (context.location.y >= Main.win.canvas.height) {
//            context.movingRight = false;
//        }
//        else if (context.location.y <= 0) {
//            context.movingRight = true;
//        }
//
//        if (context.isBigBoss == 0) {
//            if (context.movingRight) context.location.y += context.UNIT_MOVE;
//            else context.location.y += context.UNIT_MOVE;
//        }
//        else {
//            if (context.movingRight) context.location.x += context.UNIT_MOVE*3;
//            else context.location.x += context.UNIT_MOVE*3;
//        }

        context.location.y += context.UNIT_MOVE;

        if (context.location.y+135 <= 350 && context.location.y+0 >= -50){ // head before half, tail over the scene edge
            if (context == piranhaMon.currentPiranhaMon1) {
                piranhaMon.piranha1AttackingOn = true;
                InputEvent pattackevent1 = new InputEvent();
                pattackevent1.event = new EnemyCreateEvent("CompressedIceBerg",
                        (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                pattackevent1.type = InputEvent.PIRANHA_SHOOTICEBERG; // let InputEvent handle it...
                Main.playerInputEventQueue.queue.add(pattackevent1);
            }
            if (context == piranhaMon.currentPiranhaMon2) {
                piranhaMon.piranha2AttackingOn = true;
                InputEvent pattackevent2 = new InputEvent();
                pattackevent2.event = new EnemyCreateEvent("CompressedIceBerg",
                        (int)context.location.x, (int)context.location.y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
                pattackevent2.type = InputEvent.PIRANHA_SHOOTICEBERG; // let InputEvent handle it...
                Main.playerInputEventQueue.queue.add(pattackevent2);
            }
        }
        else {
            if (context == piranhaMon.currentPiranhaMon1) piranhaMon.piranha1AttackingOn = false;
            if (context == piranhaMon.currentPiranhaMon2) piranhaMon.piranha2AttackingOn = false;
        }


    }
}
