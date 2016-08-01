package com.waffel.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jonny on 7/28/16.
 */
public class GameScreen implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(GameScreen.class);
    private static final String TITLE = "Waffel World";
    private static final JFrame FRAME = new JFrame(TITLE);
    private static final JPanel MAIN_SCREEN = new JPanel();

    public static final int INITAL_WIDTH = 960;
    public static final int INITAL_HEIGHT = INITAL_WIDTH * 3 / 4;

    private static boolean RUN = true;
    private static final long FPS = 60;
    private static final long TARGET = 1000 / FPS;

    private static GameLoop gameLoop;

    private BufferedImage image;

    static {
        FRAME.setSize(INITAL_WIDTH, INITAL_HEIGHT);
        FRAME.setContentPane(MAIN_SCREEN);
        FRAME.setResizable(true);
        FRAME.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        FRAME.setLocationRelativeTo(null);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameScreen.endGame();
                GameLoop.endGame();
                FRAME.dispose();
            }
        });
    }

    public GameScreen() {
        FRAME.setVisible(true);
        MAIN_SCREEN.setFocusable(true);
        MAIN_SCREEN.requestFocus();

        image = new BufferedImage(INITAL_WIDTH, INITAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void drawToScreen() {
        Graphics graphics = MAIN_SCREEN.getGraphics();
        if (graphics != null) {
            graphics.drawImage(image, 0, 0, FRAME.getWidth(), FRAME.getHeight(), null);
            graphics.dispose();
        }
    }

    public Graphics2D getGraphics() {
        return (Graphics2D) image.getGraphics();
    }

    public static synchronized void endGame() {
        RUN = false;
    }

    @Override
    public void run() {

        long start;
        long time;
        long wait;

        while (RUN) {
            start = System.nanoTime();

            Graphics2D g = getGraphics();
            gameLoop.drawController(g);
            drawToScreen();
            g.dispose();

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

    public static void main(String[] args) {
        gameLoop = new GameLoop();
        ExecutorService service =  Executors.newFixedThreadPool(2);
        service.submit(gameLoop);
        service.submit(new GameScreen());
        service.shutdown();
    }
}
