package model.compressediceberg;

import model.bubble.Bubble;
import model.bubble.BubbleAnimStrategy;

public class CompressedIceBergAnimShooting implements CompressedIceBergAnimStrategy {

    CompressedIceBerg context;

    public CompressedIceBergAnimShooting(CompressedIceBerg context) {
        this.context = context;
    }

    @Override
    public void animate() {
        double rad = Math.atan2(context.target.y - context.location.y, context.target.x - context.location.x);
        context.location.x += context.UNIT_MOVE * Math.cos(rad); // opposite(delta x)/hypotenuse
        context.location.y += context.UNIT_MOVE * Math.sin(rad); // adjacent(delta y)/hypotenuse

    }
}
