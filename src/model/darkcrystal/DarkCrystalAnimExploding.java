package model.darkcrystal;

import java.awt.*;

public class DarkCrystalAnimExploding implements model.darkcrystal.DarkCrystalAnimStrategy {

    DarkCrystal context;

    public DarkCrystalAnimExploding(DarkCrystal context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.color = new Color(166,247,246);
        context.explodeCount++;
    }
}
