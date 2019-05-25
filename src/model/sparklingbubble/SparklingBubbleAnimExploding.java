package model.sparklingbubble;

import java.awt.*;

public class SparklingBubbleAnimExploding implements SparklingBubbleAnimStrategy {

    SparklingBubble context;

    public SparklingBubbleAnimExploding(SparklingBubble context) {
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
        context.explodeCount++;

    }
}
