package model.whirlpoolshield;

import model.waterbeam.WaterBeam;
import model.waterbeam.WaterBeamAnimStrategy;

public class WhirlpoolShieldAnimDefensing implements WhirlpoolShieldAnimStrategy {

    WhirlpoolShield context;

    public WhirlpoolShieldAnimDefensing(WhirlpoolShield context) {

        this.context = context;
    }

    @Override
    public void animate() {
        double delta_x = context.target.x - context.location.x;
        double delta_y = context.target.y - context.location.y;

        double direction = Math.atan2(delta_y, delta_x);

//        if (context.chargeCount<25) {
//            context.location.y -= context.UNIT_MOVE;
//        }
//        else {
//            context.location.y -= (context.UNIT_MOVE+1)*15;
//        }

//        if (context.chargeCount>50) {                           // update State is prior than this...
//            context.state = context.STATE_DISAPPEARING;
//        }
    }
}
