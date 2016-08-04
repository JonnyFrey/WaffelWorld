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
    // The event types for when keys are changed in state.
    enum EventType {
        PRESSED, RELEASED, TYPED;
    }

    // The instance of this singleton class' object.
    private static final Keyboard board = new Keyboard();

    /* The key pressed stack that gets updated and cleared every game iteration. To keep track of all the keys that were
     * pressed in between game iterations.
     */
    private volatile Stack<EventData> keyStack = new Stack<>();
    //The array of the state of all the keys.
    private boolean[] pressed = new boolean[255];
    // The array of the keys that were pressed in between this iteration and the past.
    private boolean[] justPressed = new boolean[255];
    // The array of the keys that were released in between this iteration and the past.
    private boolean[] justReleased = new boolean[255];
    // The LinkedList of the character of the keys that were typed in between this iteration and the past.
    private LinkedList<Character> justTyped = new LinkedList<>();


    private Keyboard() {
    }

    /**
     * Updates the arrays that keep track of the keys' states.
     * Clears the last modified lists (justPressed, justReleased ...) and pops elements from the keyStack and updates
     * the other lists accordingly.
     *
     * The justPressed / released / typed lists are only set to true for a given key if that key was not in the state
     * it is in this iteration.
     *
     * After updating the just... arrays the main state monitoring array (pressed) is updated.
     */
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


    /**
     * The keys stored in the linked list are affected by capslock and other os key modifications.
     * @return The linked list storing the characters of all the keys that were just typed.
     */
    public LinkedList<Character> getTypedCharacters() {
        return justTyped;
    }

    /**
     * Will try to return the state of a given ascii value. If the value is not in range it will return false.
     * @param ascii The ascii value of the desired key.
     * @return Weather a given key is pressed.
     */
    public boolean isPressed(int ascii) {
        return ascii >= 0 && ascii < pressed.length && pressed[ascii];
    }

    /**
     * Will try to return the state of a given ascii value. If the value is not in range it will return false.
     * @param ascii The ascii value of the desired key.
     * @return Weather a given was last pressed between now and the last game iteration.
     */
    public boolean hasJustPressed(int ascii) {
        return ascii >= 0 && ascii < justPressed.length && justPressed[ascii];
    }

    /**
     * Will try to return the state of a given ascii value. If the value is not in range it will return false.
     * @param ascii The ascii value of the desired key.
     * @return Weather a given was last released between now and the last game iteration.
     */
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

    // Returns the instance of this singleton class.
    public static Keyboard getInstence() {
        return board;
    }

    /**
     * Stores the key event and the event type (pressed, released, etc.)
     * Provides accessor methods for that information.
     */
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
