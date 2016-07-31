package com.waffel.core;

import java.awt.*;

/**
 * Created by Jonny on 7/31/16.
 */
public interface State {
    void init();

    void update();

    void draw(Graphics2D g);
}
