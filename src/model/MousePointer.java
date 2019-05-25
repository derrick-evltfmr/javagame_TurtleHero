package model;

import java.awt.*;

public class MousePointer extends GameFigure {

    public final int SIZE = 10; // size of the pointer
    public static MousePointer currentMousePointer = null;

    public MousePointer(int x, int y) { // constructor
        super (x,y); // pass x,y info to the parent class
        currentMousePointer = this;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        // draw the cross sign
        // 1. draw the horizontal line, x changing
        g2.drawLine((int) location.x - SIZE, (int)location.y,
                (int) location.x+SIZE, (int) location.y);
        // 2. draw the vertical line, y changing
        g2.drawLine((int) location.x, (int)location.y-SIZE,
                (int) location.x, (int) location.y + SIZE);

    }

    @Override
    public void update() {
        // NOT APPLICABLE FOR THIS CASE AS IT AUTO UPDATE BASE ON CURRENT POINTER LOCATION

    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }
}
