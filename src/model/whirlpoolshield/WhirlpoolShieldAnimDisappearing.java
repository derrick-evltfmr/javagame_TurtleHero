package model.whirlpoolshield;

import model.waterbeam.WaterBeam;
import model.waterbeam.WaterBeamAnimStrategy;

import java.awt.*;

public class WhirlpoolShieldAnimDisappearing implements WhirlpoolShieldAnimStrategy {

    WhirlpoolShield context;

    public WhirlpoolShieldAnimDisappearing(WhirlpoolShield context) {
        this.context = context;
    }

    @Override
    public void animate() {
//            if (context.size <= context.MAX_BUBBLE_SIZE && context.size >0) {
//                context.size += 5;
//            }
//
//            context.color = Color.GREEN;
//            context.size +=2;
//            context.size -=2;
//            while (context.size > 0) context.size -= 15;
//
//            if (context.size <0) {
//                context.state = context.STATE_DISAPPEARING;
//            }
            context.color = Color.orange;
            context.done = true;

    }
}
