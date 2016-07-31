package com.waffel.core;

import com.waffel.states.MainMenuState;

/**
 * Created by Jonny on 7/31/16.
 */
public enum Stage {

    MAIN_MENU {
        @Override
        public State getState() {
            return new MainMenuState();
        }
    };

    public abstract State getState();

}
