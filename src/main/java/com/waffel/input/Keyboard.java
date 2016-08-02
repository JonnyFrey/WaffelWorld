package com.waffel.input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * ssssssssssssssss
 * Created by yosephsa on 8/2/16.
 */
public class Keyboard implements KeyListener {
    enum EventType {
        PRESSED, RELEASED, TYPED;
    }

    private static final Keyboard board = new Keyboard();

    public static final boolean PRESSED = true;
    public static final boolean RELEASED = false;

    private volatile Stack<EventData> keyStack = new Stack<EventData>();
    private boolean[] pressed = new boolean[255];
    private boolean[] justPressed = new boolean[255];

    private Keyboard() {

    }

    public void update() {
        for (int i = 0; i < keyStack.size(); i++) {
            EventData e = keyStack.pop();
            System.out.println(e.getKeyEvent().getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyStack.push(new EventData(keyEvent, EventType.PRESSED));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyStack.push(new EventData(keyEvent, EventType.RELEASED));
    }

    public static Keyboard getInstence() {
        return board;
    }

    private class EventData {
        private KeyEvent event;
        private EventType type;

        public EventData(KeyEvent e, EventType t) {
            this.event = e;
            this.type = t;
        }

        public KeyEvent getKeyEvent() {
            return event;
        }

        public EventType getEventType() {
            return type;
        }
    }


}
