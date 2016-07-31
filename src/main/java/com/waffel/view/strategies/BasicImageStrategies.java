package com.waffel.view.strategies;

import com.waffel.core.GameScreen;
import com.waffel.view.DrawMaster;
import com.waffel.view.Drawable;
import com.waffel.view.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Jonny on 7/31/16.
 */
public class BasicImageStrategies {

    private BasicImageStrategies() {

    }

    public static DrawMaster.DrawStrategy basicImageDraw(Images images) {
        return new SingleImageStrategy(images);
    }

    public static DrawMaster.DrawStrategy basicImageWithSizeDraw(Images images) {
        return new SingleImageWithSizeStrategy(images);
    }

    public static DrawMaster.DrawStrategy fullScreenDraw(Images images) {
        return new FullScreenWrapImageStrategy(images);
    }

    private abstract static class BasicDrawImageStrategy implements DrawMaster.DrawStrategy {
        protected BufferedImage image;

        public BasicDrawImageStrategy(Images images) {
            try {
                this.image = images.loadImage();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load image " + images, e);
            }
        }
    }

    private static class SingleImageStrategy extends BasicDrawImageStrategy {

        public SingleImageStrategy(Images images) {
            super(images);
        }

        @Override
        public void draw(Graphics2D g, Drawable drawable) {
            g.drawImage(image, drawable.getX(), drawable.getY(), null);
        }
    }

    private static class SingleImageWithSizeStrategy extends BasicDrawImageStrategy {

        public SingleImageWithSizeStrategy(Images images) {
            super(images);
        }

        @Override
        public void draw(Graphics2D g, Drawable drawable) {
            g.drawImage(image, drawable.getX(), drawable.getY(), drawable.getWidth(), drawable.getHeight(), null);
        }
    }

    private static class FullScreenWrapImageStrategy extends BasicDrawImageStrategy {

        public FullScreenWrapImageStrategy(Images images) {
            super(images);
        }

        @Override
        public void draw(Graphics2D g, Drawable drawable) {
            int x = drawable.getX();
            int y = drawable.getY();
            g.drawImage(image, drawable.getX(), drawable.getY(), GameScreen.INITAL_WIDTH, GameScreen.INITAL_HEIGHT, null);

            if (x < 0) {
                g.drawImage(image, x + GameScreen.INITAL_WIDTH, y, GameScreen.INITAL_WIDTH, GameScreen.INITAL_HEIGHT, null);
            }
            if (x > 0) {
                g.drawImage(image, x - GameScreen.INITAL_WIDTH, y, GameScreen.INITAL_WIDTH, GameScreen.INITAL_HEIGHT, null);
            }
            if (y < 0) {
                g.drawImage(image, x, y + GameScreen.INITAL_HEIGHT, GameScreen.INITAL_WIDTH, GameScreen.INITAL_HEIGHT, null);
            }
            if (y > 0) {
                g.drawImage(image, x, y - GameScreen.INITAL_HEIGHT, GameScreen.INITAL_WIDTH, GameScreen.INITAL_HEIGHT, null);
            }
        }
    }
}
