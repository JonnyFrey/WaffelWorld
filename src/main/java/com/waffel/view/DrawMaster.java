package com.waffel.view;

import com.waffel.view.strategies.BasicImageStrategies;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonny on 7/31/16.
 */
public class DrawMaster {
    private Map<Drawable, DrawStrategy> instanceMap;
    private Map<Class<? extends Drawable>, DrawStrategy> classMap;
    private DrawStrategy nullStrategy;

    private DrawMaster(Builder builder) {
        this.instanceMap = builder.instanceMap;
        this.classMap = builder.classMap;
        nullStrategy = BasicImageStrategies.basicImageDraw(Images.ERROR);
    }

    public void draw(Graphics2D g, List<? extends Drawable> drawables) {
        drawables.parallelStream().forEach(o -> draw(g, o));
    }

    public void draw(Graphics2D g, Drawable drawable) {
        DrawStrategy strategy = instanceMap.get(drawable);
        if (strategy == null) {
            strategy = classMap.getOrDefault(drawable.getClass(), nullStrategy);
        }
        strategy.draw(g, drawable);
    }

    public static class Builder {

        private Map<Drawable, DrawStrategy> instanceMap;
        private Map<Class<? extends Drawable>, DrawStrategy> classMap;

        public Builder() {
            instanceMap = new HashMap<>();
            classMap = new HashMap<>();
        }

        public Builder addStrategy(Drawable drawable, DrawStrategy strategy) {
            instanceMap.put(drawable, strategy);
            return this;
        }

        public Builder addStrategy(Class<? extends Drawable> drawClass, DrawStrategy strategy) {
            classMap.put(drawClass, strategy);
            return this;
        }

        public DrawMaster build() {
            return new DrawMaster(this);
        }
    }

    @FunctionalInterface
    public interface DrawStrategy {

        void draw(Graphics2D g, Drawable drawable);

    }

}
