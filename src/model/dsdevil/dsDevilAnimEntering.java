package model.dsdevil;

import controller.InputEvent;
import controller.Main;
import controller.enemyobserver.EnemyCreateEvent;
import model.piranhamon.piranhaMon;
import model.piranhamon.piranhaMonAnimStrategy;

public class dsDevilAnimEntering implements dsDevilAnimStrategy {

    dsDevil context;

    public dsDevilAnimEntering(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
        if (context.location.y <150) {context.location.y += context.UNIT_MOVE;}
    }
}
