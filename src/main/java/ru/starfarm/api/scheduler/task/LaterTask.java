package ru.starfarm.api.scheduler.task;

import ru.starfarm.util.Cooldown;

public class LaterTask extends Cooldown implements SchedulerTask {
    private final Runnable run;

    private final int id;

    public LaterTask(int id, int delay, Runnable run) {
        super(delay, false);
        this.id = id;
        this.run = run;
    }

    public void update() {
        cancel();
        SchedulerTask.super.update();
    }

    public int getId() {
        return this.id;
    }

    public int getDelay() {
        return this.delay;
    }

    public boolean needUpdate() {
        return is();
    }

    public Runnable getRunnable() {
        return this.run;
    }
}
