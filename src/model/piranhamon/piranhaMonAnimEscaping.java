package model.piranhamon;

import model.lobstermon.lobsterMon;
import model.lobstermon.lobsterMonAnimStrategy;

import java.awt.*;

public class piranhaMonAnimEscaping implements piranhaMonAnimStrategy {

    piranhaMon context;

    public piranhaMonAnimEscaping(piranhaMon context) {
        this.context = context;
    }

    @Override
    public void animate() {
//            context.color = Color.RED;
            context.location.y -= context.UNIT_MOVE_ESCAPING;
            context.canHurt = false;
            context.eye = context.EYES_FAINT;

            if (piranhaMon.currentPiranhaMon1 == context) {
                piranhaMon.currentPiranhaMon1 = null;
                piranhaMon.callBigBoss++;
                piranhaMon.piranha1AttackingOn = false;
            }
            if (piranhaMon.currentPiranhaMon2 == context) {
                piranhaMon.currentPiranhaMon2 = null;
                piranhaMon.callBigBoss++;
                piranhaMon.piranha2AttackingOn = false;
            }
        }
}
