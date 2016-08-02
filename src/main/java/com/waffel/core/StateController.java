package com.waffel.core;

import com.google.common.base.Preconditions;

import java.awt.*;

/**
 * Created by Jonny on 7/31/16.
 */
public class StateController {
    private static State[] states;
    private static int current;

    public StateController() {
        states = new State[Stage.values().length];
        loadState(Stage.MAIN_MENU, false);
    }

    /**
     * Sets the stage to the desired state and forces to load a new
     * stage into memory. This also calls the init method to set up
     * any resources/variables. This is to be used instaed of
     * constructors to keep persistence of states
     *
     * @param stage  The Stage that contains the State to be loaded
     * @param unload if the current State should be kept in memory
     */
    public static synchronized void loadState(Stage stage, boolean unload) {
        states[stage.ordinal()] = null;
        setState(stage, unload);
    }

    /**
     * Sets the stage to the desired state and only sets up the state
     * if the stage is not already been loaded into memory. If it has not
     * been loaded. This calls the init method to set up resources.
     *
     * @param stage  The Stage that contains the State to be loaded
     * @param unload if the current State should be kept in memory
     */
    public static synchronized void setState(Stage stage, boolean unload) {
        if (unload) {
            states[current] = null;
        }
        current = stage.ordinal();
        if (states[current] == null) {
            states[current] = stage.getState();
            states[current].init();
        }
    }

    public void update(double delta) {
        getState().update(delta);
    }

    public void draw(Graphics2D g) {
        getState().draw(g);
    }

    public State getState() {
        return Preconditions.checkNotNull(states[current],
                "State Was Found Null, ID: " + current);
    }
}
