package com.waffel.model;

import com.waffel.view.Drawable;

/**
 * Created by Jonny on 7/31/16.
 */
public abstract class BasicGUI implements Drawable {

    protected double x;
    protected double y;

    protected double speedX;
    protected double speedY;

    public BasicGUI() {
        this(0, 0);
    }

    public BasicGUI(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }


    public void setVector(double speedX, double speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update() {
        x += speedX;
        y += speedY;
        fixBounds();
    }

    public abstract void fixBounds();

}
