package model.bubble;

public class BubbleAnimShooting implements BubbleAnimStrategy {

    Bubble context;

    public BubbleAnimShooting(Bubble context) {
        this.context = context;
    }

    @Override
    public void animate() {
        double rad = Math.atan2(context.target.y - context.location.y, context.target.x - context.location.x);
        context.location.x += context.UNIT_MOVE * Math.cos(rad); // opposite(delta x)/hypotenuse
        context.location.y += context.UNIT_MOVE * Math.sin(rad); // adjacent(delta y)/hypotenuse

    }
}
