package model.lobstermon;

import java.awt.*;

public class lobsterMonAnimReached implements lobsterMonAnimStrategy {

    lobsterMon context;

    public lobsterMonAnimReached(lobsterMon context) {
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
