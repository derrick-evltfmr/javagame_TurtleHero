package model.tentacle;

import model.tentacle.Tentacle;
import model.tentacle.TentacleAnimStrategy;

public class TentacleAnimReached implements TentacleAnimStrategy {

    Tentacle context;

    public TentacleAnimReached(Tentacle context) {
        this.context = context;
    }

    @Override
    public void animate() {
        if (context.winWalk<7) {
            context.winWalk++;
            context.location.y++;
        }
        if (context.winWalk>=7 && context.winWalk<13){
            context.winWalk++;
        }
        while (context.winWalk>=13 && context.winWalk<20) {
            context.location.y++;
            context.winWalk++;
        }
    }
}
