package controller;

import java.util.EventObject;

public class InputEvent { // to store each event from physical device like mouse / keyboard

    public static final int MOUSE_PRESSED = 0;
    public static final int MOUSE_MOVED = 1;
    public static final int KEY_PRESSED = 2;
    public static final int LOBM_CREATE = 3; // lobsterMon CREATE EVENT IS AN INPUT TO THE QUEUE
    public static final int STAR_CREATE = 4; // star CREATE EVENT IS AN INPUT TO THE QUEUE

    public static final int PIRANHA_PHASE = 5;
    public static final int PIRANHA_SHOOTICEBERG = 6;

    public static final int BOSS_APPEAR = 7;
    public static final int DSDEVIL_PHASE = 8;
    public static final int DSDEVIL_NORMAL_DC = 9;
    public static final int DSDEVIL_COMBO_DB = 10;
    public static final int DSDEVIL_TENTACLE_STRETCH = 11;

    public static final int CONGRATULATIONS = 99;

    public static final int DO_NOTHING = -1;

    public EventObject event;
    public int type;


}
