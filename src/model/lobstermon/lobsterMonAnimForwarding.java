package model.lobstermon;

import controller.Main;

public class lobsterMonAnimForwarding implements lobsterMonAnimStrategy {

    lobsterMon context;

    public lobsterMonAnimForwarding(lobsterMon context) {
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

    }
}
