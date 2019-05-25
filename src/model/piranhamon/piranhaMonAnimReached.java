package model.piranhamon;

import model.lobstermon.lobsterMon;
import model.lobstermon.lobsterMonAnimStrategy;

public class piranhaMonAnimReached implements piranhaMonAnimStrategy {

    piranhaMon context;

    public piranhaMonAnimReached(piranhaMon context) {
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
