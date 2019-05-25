package model.waterbeam;

import model.sparklingbubble.SparklingBubble;
import model.sparklingbubble.SparklingBubbleAnimStrategy;

public class WaterBeamAnimShooting implements WaterBeamAnimStrategy {

    WaterBeam context;

    public WaterBeamAnimShooting(WaterBeam context) {

        this.context = context;
    }

    @Override
    public void animate() {
        double delta_x = context.target.x - context.location.x;
        double delta_y = context.target.y - context.location.y;

        double direction = Math.atan2(delta_y, delta_x);

        if (context.chargeCount<25) {
            context.location.y -= context.UNIT_MOVE;
        }
        else {
            context.location.y -= (context.UNIT_MOVE+1)*25;
        }
    }
}
