package model.sparklingbubble;

public class SparklingBubbleAnimShooting implements SparklingBubbleAnimStrategy {

    SparklingBubble context;

    public SparklingBubbleAnimShooting(SparklingBubble context) {

        this.context = context;
    }

    @Override
    public void animate() {
        double delta_x = context.target.x - context.location.x;
        double delta_y = context.target.y - context.location.y;

        double direction = Math.atan2(delta_y, delta_x);

        if (context.chargeCount<35){
            if (context.chargeCount%3==0) {
                context.location.x += context.UNIT_MOVE * Math.cos(direction); // opposite(delta x)/hypotenuse
                context.location.y += context.UNIT_MOVE * Math.sin(direction); // adjacent(delta y)/hypotenuse
            }
        }

        if (context.chargeCount>=35) {
            context.location.x += context.UNIT_MOVE * Math.cos(direction); // opposite(delta x)/hypotenuse
            context.location.y += context.UNIT_MOVE * Math.sin(direction); // adjacent(delta y)/hypotenuse
        }
    }
}
