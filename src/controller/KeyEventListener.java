package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventListener extends KeyAdapter {


    private int spaceCount = 0;


    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (Main.gamePaused != true || e.getKeyCode()==KeyEvent.VK_W) { // Not Paused, or W(resume) key
            InputEvent inputEvent = new InputEvent();
            inputEvent.event = e;
            inputEvent.type = InputEvent.KEY_PRESSED;
            Main.playerInputEventQueue.queue.add(inputEvent);
            // do the same thing as mouseeventlistener, let playerinputeventqueue to deal with them
        }

        }
    }

