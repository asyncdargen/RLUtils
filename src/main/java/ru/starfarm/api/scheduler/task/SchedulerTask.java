package ru.starfarm.api.scheduler.task;

import ru.starfarm.mod.SFUtils;

public interface SchedulerTask {
    int getId();

    int getDelay();

    boolean needUpdate();

    Runnable getRunnable();

    default boolean cancel() {
        return SFUtils.INSTANCE.getScheduler().cancelTask(getId());
    }

    default boolean isCancelled() {
        return !SFUtils.INSTANCE.getScheduler().isRunningTask(getId());
    }

    default void update() {
        getRunnable().run();
    }
}
