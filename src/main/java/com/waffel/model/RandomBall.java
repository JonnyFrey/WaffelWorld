package com.waffel.model;

import com.waffel.core.GameScreen;

/**
 * Created by Jonny on 7/31/16.
 */
public class RandomBall extends Entity {

    public RandomBall() {
        this.x = (int) (Math.random() * GameScreen.INITAL_WIDTH);
        this.y = (int) (Math.random() * GameScreen.INITAL_HEIGHT);
        setVector(Math.random() * 500, Math.random() * 500);
    }

    @Override
    public int getWidth() {
        return 10;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        if (x > GameScreen.INITAL_WIDTH - getWidth()) {
            x = (double) GameScreen.INITAL_WIDTH - getWidth();
            setVector(-speedX, speedY);
        }
        if (y > GameScreen.INITAL_HEIGHT - getHeight() * 3) {
            y = (double) GameScreen.INITAL_HEIGHT - getHeight() * 3;
            setVector(speedX, -speedY);
        }
        if (x < 0) {
            x = 0;
            setVector(-speedX, speedY);
        }
        if (y < 0) {
            y = 0;
            setVector(speedX, -speedY);
        }
    }

    @Override
    public String toString() {
        return "RandomBall{ x:" + x + " y:" + y + " dx:" + speedX + " dy:" + speedY + "}";
    }
}
