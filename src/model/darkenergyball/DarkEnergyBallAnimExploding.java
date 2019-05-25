package model.darkenergyball;

import model.darkenergyball.DarkEnergyBall;

import java.awt.*;

public class DarkEnergyBallAnimExploding implements model.darkenergyball.DarkEnergyBallAnimStrategy {

    DarkEnergyBall context;

    public DarkEnergyBallAnimExploding(DarkEnergyBall context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.explodeCount++;

    }
}
