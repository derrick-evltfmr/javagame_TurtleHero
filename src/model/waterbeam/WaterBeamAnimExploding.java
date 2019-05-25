package model.waterbeam;

import model.sparklingbubble.SparklingBubble;
import model.sparklingbubble.SparklingBubbleAnimStrategy;

import java.awt.*;

public class WaterBeamAnimExploding implements WaterBeamAnimStrategy {

    WaterBeam context;

    public WaterBeamAnimExploding(WaterBeam context) {
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
