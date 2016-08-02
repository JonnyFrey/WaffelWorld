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
public class ConstantRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConstantRunnable.class);
    private static final DecimalFormat DF2 = new DecimalFormat(".##");

    private boolean run = true;
    private final long fps;
    private final long target;
    private final Consumer<ConstantRunnable> apply;

    private List<Long> waitCount = Lists.newLinkedList();
    private List<Long> timeCount = Lists.newLinkedList();
    private int framesPerLog;
    private int frameCount;
    private long time;
    private long wait;

    private String name = "Constant Runnable";

    public ConstantRunnable(long targetTime, Consumer<ConstantRunnable> apply) {
        Preconditions.checkArgument(targetTime >= 0);
        fps = targetTime;
        target = 1000 / fps;
        this.apply = apply;
    }

    @Override
    public void run() {
        long start;
        while (run) {
            start = System.nanoTime();

            apply.accept(this);

            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

            wait = target - time;
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
        if (framesPerLog >= 0 && --frameCount <= 0) {
            OptionalDouble timeAvg = timeCount.parallelStream().mapToLong(Long::byteValue).average();
            OptionalDouble waitAvg = waitCount.parallelStream().mapToLong(Long::byteValue).average();
            timeAvg.ifPresent(value -> log("Time Avg : " + DF2.format(value) + " ms"));
            waitAvg.ifPresent(value -> log("Wait Avg : " + DF2.format(value) + " ms"));
//            log("Delta: " + getDelta());
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

    public synchronized void endRun() {
        run = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void log(String message) {
        LOGGER.info(name + "\t" + message);
    }

    public double getDelta() {
        if (wait > 0) {
            return time + wait;
        } else {
            return time;
        }
    }

}
