package com.waffel.core;

import com.waffel.util.ConstantRunnable;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jonny on 7/18/16.
 */
public class GameLoop {

    private static final int FPS = 60;

    private StateController controller;
    private Lock lock;

    public GameLoop() {
        controller = new StateController();
        lock = new ReentrantLock();
    }

    public void drawController(Graphics2D g) {
            controller.draw(g);
    }

    private static ConstantRunnable getGameLoopRunnable(final GameLoop gameLoop) {
        return new ConstantRunnable(FPS, (runnable) -> {
            gameLoop.controller.update(runnable.getDelta());
        });
    }

    private static ConstantRunnable getGraphicRunnable(final GameScreen screen, final GameLoop gameLoop) {
        return new ConstantRunnable(FPS, runnable -> {
            Graphics2D g = screen.getGraphics();
            gameLoop.drawController(g);
            screen.drawToScreen();
            g.dispose();
        });
    }

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        GameScreen gameScreen = new GameScreen();
        ConstantRunnable gameLoopConstantRunnable = getGameLoopRunnable(gameLoop);
        ConstantRunnable gameScreenConstantRunnable =
                getGraphicRunnable(gameScreen, gameLoop);

        gameLoopConstantRunnable.setName("Loop");
        gameScreenConstantRunnable.setName("Screen");

        gameScreen.addConstantRunnable(gameScreenConstantRunnable)
                .addConstantRunnable(gameLoopConstantRunnable);

        Thread gameLoopThread = new Thread(gameLoopConstantRunnable);
        Thread gameScreenThread = new Thread(gameScreenConstantRunnable);
        gameLoopThread.setName("GameLoop");
        gameScreenThread.setName("GameScreen");
        gameLoopThread.start();
        gameScreenThread.start();
    }

}
