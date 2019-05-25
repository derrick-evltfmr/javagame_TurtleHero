package model.bubble;

import controller.Main;
import model.GameFigure;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bubble extends GameFigure {

    public static final int UNIT_MOVE = 15;
    public static final int INIT_BUBBLE_SIZE = 10;
    public static final int MAX_BUBBLE_SIZE = 30;

    public static final int STATE_SHOOTING = 0;
    public static final int STATE_EXPLODING = 1;
    public static final int STATE_DONE = 2;


    int size = INIT_BUBBLE_SIZE;
    Point2D.Float target; // where mouse was pressed, get target location and draw the path towards it
    Point2D.Float longTarget;
    Color color; // color of the bubble

    int state;

    BubbleAnimStrategy animStrategy;


    boolean endExplosion = false;


    public Bubble(int tx, int ty) { // x->tx, y->ty, target, that is, mouse press location

        turtleHero turtleHero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

        //initial location of the bubble <- the end of the barrel
        super.location.x = turtleHero.location.x -14; // almost the nose of turtle
        super.location.y = turtleHero.location.y +1; // almost the nose of turtle

        target = new Point2D.Float(tx, ty); // mousepress location as target
        color = new Color(154,224,222); // initial color

        state = STATE_SHOOTING; // initial state

        animStrategy = new BubbleAnimShooting(this); // pass the bubble obj itself to strategy

        friendINDEX = 2;

    }

    @Override
    public void render(Graphics2D g2) {
//        color = new Color(24,224,222);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2)); // change the thickness back to 1
        g2.fillOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
        color = new Color(233,230,224); g2.setColor(color);
        g2.drawOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);
        color = new Color(154,224,222);
    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate(); // Strategy design pattern

//        if (state == STATE_DEFENSING) {
//            double rad = Math.atan2(target.y - location.y, target.x - location.x);
//            location.x += UNIT_MOVE * Math.cos(rad); // opposite(delta x)/hypotenuse
//            location.y += UNIT_MOVE * Math.sin(rad); // adjacent(delta y)/hypotenuse
//        }
//        else if (state == STATE_DISAPPEARING) {
//            color = Color.YELLOW;
//            size++;
//        }

//
    }

    private void updateState() {
        if (state == STATE_SHOOTING ) { // travelling mode
            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                animStrategy = new BubbleAnimExploding(this);
            }
        }
        else if (state == STATE_EXPLODING) {
            if (size >= MAX_BUBBLE_SIZE) {
                state = STATE_DONE;
            }
        }
        else if (state == STATE_DONE) {
            super.done = true;
        }

    }

    @Override
    public int getCollisionRadius() {
        return size/2;
    }
}
