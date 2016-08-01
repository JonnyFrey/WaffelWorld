package com.waffel.util;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by Jonny on 7/31/16.
 */
public class ConstantRunnable<T> implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConstantRunnable.class);
    private static final DecimalFormat DF2 = new DecimalFormat(".##");

    private boolean run = true;
    private final long FPS;
    private final long TARGET;
    private final Consumer<T> apply;
    private final T contex;

    private List<Long> waitCount = Lists.newLinkedList();
    private List<Long> timeCount = Lists.newLinkedList();
    private int framesPerLog;
    private int frameCount;

    public ConstantRunnable(long targetTime, Consumer<T> apply, T contex) {
        Preconditions.checkArgument(targetTime >= 0);
        FPS = targetTime;
        TARGET = 1000 / FPS;
        this.apply = apply;
        this.contex = contex;
    }

    public T getContex() {
        return contex;
    }

    @Override
    public void run() {
        long start;
        long time;
        long wait;
        while (run) {
            start = System.nanoTime();

            apply.accept(contex);

            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

            wait = TARGET - time;
            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    LOGGER.error(e);
                }
            }
            printLogs(time, wait);
        }
    }

    public void printLogs(long time, long wait) {
        if (framesPerLog != 0 && --frameCount <= 0) {
            OptionalDouble timeAvg = timeCount.parallelStream().mapToLong(Long::byteValue).average();
            OptionalDouble waitAvg = waitCount.parallelStream().mapToLong(Long::byteValue).average();
            timeAvg.ifPresent(value -> log("Time Avg : " + DF2.format(value) + " ms"));
            waitAvg.ifPresent(value -> log("Wait Avg : " + DF2.format(value) + " ms"));
            timeCount.clear();
            waitCount.clear();
            frameCount = framesPerLog;
        } else {
            timeCount.add(time);
            waitCount.add(wait);
        }
    }

    public void enableLogging(int framesPerLog) {
        this.framesPerLog = framesPerLog;
        this.frameCount = framesPerLog;
    }

    public void endRun() {
        run = false;
    }

    private void log(String message) {
        LOGGER.info(contex.getClass().getSimpleName() + "\t" + message);
    }

}
