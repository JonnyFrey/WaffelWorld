package com.waffel.model;

import com.waffel.view.Drawable;

/**
 * Created by Jonny on 7/31/16.
 */
public abstract class Entity implements Drawable {

    //Position
    protected double x;
    protected double y;

    //Speed
    protected double speedX;
    protected double speedY;

    public Entity() {
        this(0, 0);
    }

    public Entity(int x, int y) {
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
    }
}
