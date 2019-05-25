package model.tentacle;

import model.tentacle.Tentacle;
import model.tentacle.TentacleAnimStrategy;

import java.awt.*;

public class TentacleAnimEscaping implements TentacleAnimStrategy {

    Tentacle context;

    public TentacleAnimEscaping(Tentacle context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.color = Color.RED;
        context.location.y -= context.UNIT_MOVE_ESCAPING;
        context.canHurt = false;
        Tentacle.currentLobsterMon =null;
    }
}
