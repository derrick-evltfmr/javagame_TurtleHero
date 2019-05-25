package model.lobstermon;

import java.awt.*;

public class lobsterMonAnimEscaping implements lobsterMonAnimStrategy {

    lobsterMon context;

    public lobsterMonAnimEscaping(lobsterMon context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.color = Color.RED;
        context.location.y -= context.UNIT_MOVE_ESCAPING;
        context.canHurt = false;
        context.eye = context.EYES_FAINT;
        lobsterMon.currentLobsterMon =null;
    }
}
