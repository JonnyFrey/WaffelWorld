package com.waffel.model;

import com.waffel.input.KeyBinder;

/**
 * Created by yosephsa on 8/2/16.
 */
public class Player extends Entity{

    private final int WIDTH = 30, HEIGHT = 60;
    private int walkingSpeed = 3;

    public Player() {
        super();
    }
    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {
        int x, y;
        if(KeyBinder.getInstance().isBindingActive(KeyBinder.Binding.MV_FORWARD))
            y = -walkingSpeed;
        else if(KeyBinder.getInstance().isBindingActive(KeyBinder.Binding.MV_BACKWARD))
            y = walkingSpeed;
        else
            y = 0;

        if(KeyBinder.getInstance().isBindingActive(KeyBinder.Binding.MV_RIGHT))
            x = walkingSpeed;
        else if(KeyBinder.getInstance().isBindingActive(KeyBinder.Binding.MV_LEFT))
            x = -walkingSpeed;
        else
            x = 0;

        if(KeyBinder.getInstance().isBindingActive(KeyBinder.Binding.MV_BOOST)) {
            x *= 2;
            y *= 2;
        }

        setVector(x,y);
        super.update();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public String toString() {
        return "Player{ x:" + x + " y:" + y + " dx:" + speedX + " dy:" + speedY + "}";
    }
}
