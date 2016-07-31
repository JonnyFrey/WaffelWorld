package com.waffel.model;

import com.waffel.core.GameScreen;

/**
 * Created by Jonny on 7/31/16.
 */
public class Background extends BasicGUI {

    @Override
    public void fixBounds() {
        if (x > getWidth()) {
            x = -getWidth();
        }
        if (y > getHeight()) {
            y = -getHeight();
        }
        if (x < -getWidth()) {
            x = getWidth();
        }
        if (y < -getHeight()) {
            y = getHeight();
        }
    }

    @Override
    public int getWidth() {
        return GameScreen.INITAL_WIDTH;
    }

    @Override
    public int getHeight() {
        return GameScreen.INITAL_HEIGHT;
    }
}
