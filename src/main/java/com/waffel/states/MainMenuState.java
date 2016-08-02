package com.waffel.states;

import com.waffel.core.GameScreen;
import com.waffel.core.State;
import com.waffel.model.Background;
import com.waffel.model.Entity;
import com.waffel.model.RandomBall;
import com.waffel.view.DrawMaster;
import com.waffel.view.Images;
import com.waffel.view.strategies.BasicImageStrategies;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jonny on 7/31/16.
 */
public class MainMenuState implements State {

    private DrawMaster drawMaster;

    private Background clouds;
    private Background mountains;
    private Background sky;

    private List<RandomBall> randomBallList;

    @Override
    public void init() {

        clouds = new Background();
        clouds.setVector(GameScreen.INITAL_WIDTH / 10, 0);
        mountains = new Background();
        mountains.setVector(GameScreen.INITAL_WIDTH/ 20, 0);
        sky = new Background();
        sky.setVector(GameScreen.INITAL_WIDTH / 40, 0);

        drawMaster = new DrawMaster.Builder()
                .addStrategy(clouds, BasicImageStrategies.fullScreenDraw(Images.CLOUD_BACKGROUND))
                .addStrategy(mountains, BasicImageStrategies.fullScreenDraw(Images.MOUNTAINS_BACKGROUND))
                .addStrategy(sky, BasicImageStrategies.fullScreenDraw(Images.SKY_BACKGROUND))
                .addStrategy(RandomBall.class, BasicImageStrategies.basicImageWithSizeDraw(Images.ARTIFACT))
                .build();

        randomBallList = new LinkedList<>();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            randomBallList.add(new RandomBall());
        }
    }

    @Override
    public void update(double delta) {
        clouds.update(delta);
        mountains.update(delta);
        sky.update(delta);
        randomBallList.parallelStream().forEach(randomBall -> randomBall.update(delta));
    }

    @Override
    public void draw(Graphics2D g) {
        drawMaster.draw(g, sky);
        drawMaster.draw(g, clouds);
        drawMaster.draw(g, mountains);
        drawMaster.draw(g, randomBallList);
    }
}
