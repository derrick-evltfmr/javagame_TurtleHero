package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
//        System.out.println("mouse pressed at " + e.getX() + " " + e.getY());
        InputEvent inputEvent = new InputEvent();
        inputEvent.event = e; // .event is of EventObject type in InputEvent
        inputEvent.type = InputEvent.MOUSE_PRESSED; // .event store the e Event and .type store the type
        Main.playerInputEventQueue.queue.add(inputEvent);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
//        System.out.println("mouse moved at " + e.getX() + " " + e.getY());
        InputEvent inputEvent = new InputEvent();
        inputEvent.event = e;
        inputEvent.type =InputEvent.MOUSE_MOVED;
        Main.playerInputEventQueue.queue.add(inputEvent);


    }
}
