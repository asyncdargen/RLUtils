package ru.starfarm.js.reference;

import lombok.val;
import ru.starfarm.api.scheduler.Scheduler;
import ru.starfarm.api.scheduler.SchedulerProvider;
import ru.starfarm.api.scheduler.task.SchedulerTask;
import ru.starfarm.mod.SFUtils;

import java.util.ArrayList;
import java.util.List;

public class SchedulerReference implements JavaScriptReference {

    private List<Integer> active;
    private SchedulerProvider original;

    public SchedulerReference() {
        active = new ArrayList<>();
        original = SFUtils.INSTANCE.getScheduler();
    }

    public void load() {}

    public void unload() {
        synchronized (Scheduler.class) {
            active.forEach(original::cancelTask);
        }
    }

    public String getName() {
        return "Scheduler";
    }

    public SchedulerTask runTaskTimer(int delay, Runnable run) {
        val task = original.runTaskTimer(delay, run);
        active.add(task.getId());
        return task;

    };

    public SchedulerTask runTaskLater(int delay, Runnable run) {
        val task = original.runTaskLater(delay, run);
        active.add(task.getId());
        return task;
    }

    public SchedulerTask runTask(Runnable run) {
        val task = original.runTask(run);
        active.add(task.getId());
        return task;
    }

}
