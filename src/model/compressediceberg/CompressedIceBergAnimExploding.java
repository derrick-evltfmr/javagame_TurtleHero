package model.compressediceberg;

import model.bubble.Bubble;
import model.bubble.BubbleAnimStrategy;

import java.awt.*;

public class CompressedIceBergAnimExploding implements CompressedIceBergAnimStrategy {

    CompressedIceBerg context;

    public CompressedIceBergAnimExploding(CompressedIceBerg context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.color = new Color(166,247,246);
        context.explodeCount++;
    }
}
