package ru.starfarm.api.scheduler;

import ru.starfarm.api.scheduler.task.SchedulerTask;

public interface SchedulerProvider {
    SchedulerTask runTaskTimer(int delay, Runnable run);

    SchedulerTask runTaskLater(int delay, Runnable run);

    SchedulerTask runTask(Runnable run);

    boolean cancelTask(int id);

    boolean cancelAllTasks();

    boolean isRunningTask(int id);
}
