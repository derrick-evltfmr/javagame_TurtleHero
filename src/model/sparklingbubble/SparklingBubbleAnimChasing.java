package model.sparklingbubble;

import model.GameFigure;
import model.lobstermon.lobsterMon;

import java.awt.geom.Point2D;

public class SparklingBubbleAnimChasing implements SparklingBubbleAnimStrategy {

    SparklingBubble context;

    public SparklingBubbleAnimChasing(SparklingBubble context) {

        this.context = context;
    }

    @Override
    public void animate() {

        if (context.closestEnemy != null) {

            GameFigure chasingEnemy = context.closestEnemy;
            Point2D.Float chasingTarget = new Point2D.Float(chasingEnemy.location.x, chasingEnemy.location.y);

            double delta_x = chasingTarget.x - context.location.x;
            double delta_y = chasingTarget.y - context.location.y;

            double direction = Math.atan2(delta_y, delta_x);

            context.location.x += (context.UNIT_MOVE ) * Math.cos(direction); // opposite(delta x)/hypotenuse
            context.location.y += (context.UNIT_MOVE ) * Math.sin(direction); // adjacent(delta y)/hypotenuse
        }

        else
            context.animStrategy = new SparklingBubbleAnimShooting(context);


    }
}
