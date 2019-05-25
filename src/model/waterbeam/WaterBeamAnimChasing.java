package model.waterbeam;

import model.lobstermon.lobsterMon;
import model.sparklingbubble.SparklingBubble;
import model.sparklingbubble.SparklingBubbleAnimShooting;
import model.sparklingbubble.SparklingBubbleAnimStrategy;

import java.awt.geom.Point2D;

public class WaterBeamAnimChasing implements WaterBeamAnimStrategy {

    WaterBeam context;

    public WaterBeamAnimChasing(WaterBeam context) {

        this.context = context;
    }

    @Override
    public void animate() {

        if (lobsterMon.currentLobsterMon != null) {
            lobsterMon chasingLobsterMon = lobsterMon.currentLobsterMon;
            Point2D.Float chasingTarget = new Point2D.Float(chasingLobsterMon.location.x, chasingLobsterMon.location.y);

            double delta_x = chasingTarget.x - context.location.x;
            double delta_y = chasingTarget.y - context.location.y;

            double direction = Math.atan2(delta_y, delta_x);

            context.location.x += (context.UNIT_MOVE * 3) * Math.cos(direction); // opposite(delta x)/hypotenuse
            context.location.y += (context.UNIT_MOVE * 3) * Math.sin(direction); // adjacent(delta y)/hypotenuse
        }

        else
            context.animStrategy = new WaterBeamAnimShooting(context);


    }
}
