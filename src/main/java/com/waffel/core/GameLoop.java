package com.waffel.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jonny on 7/18/16.
 */
public class GameLoop implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(GameLoop.class);

    private static boolean RUN = true;
    private static final long FPS = 60;
    private static final long TARGET = 1000 / FPS;

    private StateController controller;
    private Lock lock;

    public GameLoop() {
        controller = new StateController();
        lock = new ReentrantLock();
    }

    @Override
    public void run() {

        long start;
        long time;
        long wait;
        while (RUN) {
            start = System.nanoTime();

            lock.lock();
            controller.update();
            lock.unlock();

            time = System.nanoTime() - start;

            wait = TARGET - TimeUnit.NANOSECONDS.toMillis(time);
            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

    public static synchronized void endGame() {
        RUN = false;
    }

    public void drawController(Graphics2D g) {
        if (lock.tryLock()) {
            controller.draw(g);
            lock.unlock();
        }
    }

}
