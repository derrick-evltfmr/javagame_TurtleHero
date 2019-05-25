package model.bubble;

import java.awt.*;

public class BubbleAnimExploding implements BubbleAnimStrategy {

    Bubble context;

    public BubbleAnimExploding(Bubble context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.color = new Color(166,247,246);
        context.size++;
    }
}
