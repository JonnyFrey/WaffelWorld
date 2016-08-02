package com.waffel.input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import static com.waffel.input.Keyboard.EventType.*;


/**
 * ssssssssssssssss
 * Created by yosephsa on 8/2/16.
 */
public class Keyboard implements KeyListener {
    enum EventType {
        PRESSED, RELEASED, TYPED;
    }

    private static final Keyboard board = new Keyboard();

    private volatile Stack<EventData> keyStack = new Stack<>();
    private boolean[] pressed = new boolean[255];
    private boolean[] justPressed = new boolean[255];
    private boolean[] justReleased = new boolean[255];
    private LinkedList<Character> justTyped = new LinkedList<>();


    private Keyboard() {

    }

    public void update() {
        justPressed = new boolean[255];
        justReleased = new boolean[255];
        justTyped.clear();

        for (int i = 0; i < keyStack.size(); i++) {
            EventData e = keyStack.pop();
            if (e.getEventType() == RELEASED && isPressed(e.getKeyEvent().getKeyCode())) {
                justReleased[e.getKeyEvent().getKeyCode()] = true;
                pressed[e.getKeyEvent().getKeyCode()] = false;
            } else if (e.getEventType() == PRESSED && !isPressed(e.getKeyEvent().getKeyCode())) {
                justPressed[e.getKeyEvent().getKeyCode()] = true;
                pressed[e.getKeyEvent().getKeyCode()] = true;
            } else if (e.getEventType() == TYPED)
                justTyped.add(e.getKeyEvent().getKeyChar());
        }
    }


    public LinkedList getTypedCharacters() {
        return justTyped;
    }

    public boolean isPressed(int ascii) {
        return ascii >= 0 && ascii < pressed.length && pressed[ascii];
    }

    public boolean hasJustPressed(int ascii) {
        return ascii >= 0 && ascii < justPressed.length && justPressed[ascii];
    }

    public boolean hasJustReleased(int ascii) {
        return ascii >= 0 && ascii < justReleased.length && justReleased[ascii];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        keyStack.push(new EventData(keyEvent, TYPED));
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!isPressed(keyEvent.getKeyCode()))
            keyStack.push(new EventData(keyEvent, PRESSED));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (isPressed(keyEvent.getKeyCode()))
            keyStack.push(new EventData(keyEvent, RELEASED));
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
