package ru.redline.js.reference;

import lombok.val;
import ru.redline.api.scheduler.Scheduler;
import ru.redline.api.scheduler.SchedulerProvider;
import ru.redline.api.scheduler.task.SchedulerTask;
import ru.redline.mod.RLUtils;

import java.util.ArrayList;
import java.util.List;

public class SchedulerReference implements JavaScriptReference {

    private List<Integer> active;
    private SchedulerProvider original;

    public SchedulerReference() {
        active = new ArrayList<>();
        original = RLUtils.INSTANCE.getScheduler();
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
