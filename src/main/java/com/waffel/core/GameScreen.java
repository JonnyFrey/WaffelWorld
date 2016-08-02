package com.waffel.core;

import com.beust.jcommander.internal.Lists;
import com.waffel.util.ConstantRunnable;
import com.waffel.view.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Jonny on 7/28/16.
 */
public class GameScreen {

    private static final String TITLE = "Waffel World";
    private static final JFrame FRAME = new JFrame(TITLE);
    private static final JPanel MAIN_SCREEN = new JPanel();

    public static final int INITAL_WIDTH = 960;
    public static final int INITAL_HEIGHT = INITAL_WIDTH * 3 / 4;

    private static List<ConstantRunnable> killThreads = Lists.newArrayList();

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
                killThreads.forEach(ConstantRunnable::endRun);
                FRAME.dispose();
            }
        });
    }

    public GameScreen () {
        FRAME.setVisible(true);
        MAIN_SCREEN.setFocusable(true);
        MAIN_SCREEN.requestFocus();

        image = Images.getCompatableImage(INITAL_WIDTH, INITAL_HEIGHT);
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

    public GameScreen addConstantRunnable(ConstantRunnable runnable) {
        killThreads.add(runnable);
        return this;
    }
}
