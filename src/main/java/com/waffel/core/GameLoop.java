package com.waffel.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jonny on 7/18/16.
 */
public class GameLoop implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(GameLoop.class);

    private static boolean RUN = true;
    private static final long FPS = 60;
    private static final long TARGET = 1000 / FPS;

    private StateController controller;
    private GameScreen screen;

    public GameLoop() {
        screen = new GameScreen();
        controller = new StateController();
    }

    @Override
    public void run() {

        long start;
        long time;
        long wait;
        while (RUN) {
            start = System.nanoTime();

            controller.update();
            Graphics2D g = screen.getGraphics();

            controller.draw(g);
            screen.drawToScreen();

            time = System.nanoTime() - start;

            wait = TARGET - TimeUnit.NANOSECONDS.toMillis(time);
            if (wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
        LOGGER.info("Game Is Ending. Have a nice Day");
    }

    public static synchronized void endGame() {
        LOGGER.error("End Game has been Called");
        RUN = false;
    }

    public static void main(String[] args) {
        Thread mainGameThread = new Thread(new GameLoop());
        mainGameThread.start();
        mainGameThread.setName("Main Game Thread");
    }

}
