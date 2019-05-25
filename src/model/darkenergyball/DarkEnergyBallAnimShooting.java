package model.darkenergyball;

import model.darkenergyball.DarkEnergyBall;
import model.darkenergyball.DarkEnergyBallAnimStrategy;

public class DarkEnergyBallAnimShooting implements DarkEnergyBallAnimStrategy {

    DarkEnergyBall context;

    public DarkEnergyBallAnimShooting(DarkEnergyBall context) {
        this.context = context;
    }

    @Override
    public void animate() {
        if (context.chargeCount>=4){
        double rad = Math.atan2(context.target.y - context.location.y, context.target.x - context.location.x);
        context.location.x += context.DB_UNITMOVE * Math.cos(rad); // opposite(delta x)/hypotenuse
        context.location.y += context.DB_UNITMOVE * Math.sin(rad); // adjacent(delta y)/hypotenuse

        }
    }
}
