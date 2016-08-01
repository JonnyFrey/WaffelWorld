package com.waffel.core;

import com.waffel.util.ConstantRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        if (lock.tryLock()) {
            controller.draw(g);
            lock.unlock();
        }
    }

    private static ConstantRunnable<GameLoop> getGameLoopRunnable() {
        GameLoop gameLoop = new GameLoop();
        return new ConstantRunnable<>(FPS, gameLoop1 -> {
            gameLoop.lock.lock();
            gameLoop.controller.update();
            gameLoop.lock.unlock();
        }, gameLoop);
    }

    private static ConstantRunnable<GameScreen> getGraphicRunnable(GameLoop gameLoop) {
        GameScreen gameScreen = new GameScreen(gameLoop);
        return new ConstantRunnable<>(FPS, gameScreen1 -> {
            Graphics2D g = gameScreen1.getGraphics();
            gameScreen1.getGameLoop().drawController(g);
            gameScreen1.drawToScreen();
            g.dispose();
        }, gameScreen);
    }

    public static void main(String[] args) {
        ConstantRunnable<GameLoop> gameLoopConstantRunnable = getGameLoopRunnable();
        ConstantRunnable<GameScreen> gameScreenConstantRunnable =
                getGraphicRunnable(gameLoopConstantRunnable.getContex());

        gameLoopConstantRunnable.enableLogging(60);
        gameScreenConstantRunnable.enableLogging(60);

        gameScreenConstantRunnable.getContex()
                .addConstantRunnable(gameScreenConstantRunnable)
                .addConstantRunnable(gameLoopConstantRunnable);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(gameLoopConstantRunnable);
        service.submit(gameScreenConstantRunnable);
        service.shutdown();
    }

}
