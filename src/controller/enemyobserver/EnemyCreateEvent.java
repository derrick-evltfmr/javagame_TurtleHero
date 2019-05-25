package controller.enemyobserver;

import java.util.EventObject;

public class EnemyCreateEvent extends EventObject {

    private int x;
    private int y;


    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnemyCreateEvent(Object source, int x, int y) { // we also have x y location
        super(source);
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}
}
