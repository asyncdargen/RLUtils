package ru.redline.api.scheduler.task;

import ru.redline.util.Cooldown;

public class TimerTask extends Cooldown implements SchedulerTask {
    private final Runnable run;

    private final int id;

    public TimerTask(int id, int delay, Runnable run) {
        super(delay, true);
        this.id = id;
        this.run = run;
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
