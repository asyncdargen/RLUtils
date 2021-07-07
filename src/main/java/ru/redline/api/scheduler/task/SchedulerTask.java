package ru.redline.api.scheduler.task;

import ru.redline.mod.RLUtils;

public interface SchedulerTask {
    int getId();

    int getDelay();

    boolean needUpdate();

    Runnable getRunnable();

    default boolean cancel() {
        return RLUtils.INSTANCE.getScheduler().cancelTask(getId());
    }

    default boolean isCancelled() {
        return !RLUtils.INSTANCE.getScheduler().isRunningTask(getId());
    }

    default void update() {
        getRunnable().run();
    }
}
