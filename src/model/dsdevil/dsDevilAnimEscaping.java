package model.dsdevil;

import model.piranhamon.piranhaMon;
import model.piranhamon.piranhaMonAnimStrategy;

import java.awt.*;

public class dsDevilAnimEscaping implements dsDevilAnimStrategy {

    dsDevil context;

    public dsDevilAnimEscaping(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
            context.color = Color.RED;
            context.eye = context.EYE_ESCAPE;
            context.mouth = context.MOUTH_BIG;
            context.location.y -= context.UNIT_MOVE_ESCAPING;
            context.canHurt = false;
        }
}
