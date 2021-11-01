package ru.starfarm.api.scheduler;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.logging.log4j.Logger;
import ru.starfarm.api.scheduler.task.LaterTask;
import ru.starfarm.api.scheduler.task.SchedulerTask;
import ru.starfarm.api.scheduler.task.TimerTask;
import ru.starfarm.mod.SFUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scheduler extends Thread implements SchedulerProvider {

    private final Map<Integer, SchedulerTask> tasks = new HashMap<>();

    private final Logger logger;

    private int next_id = 0;

    public Scheduler(SFUtils mod) {
        super("Tick scheduler thread");
        this.logger = mod.getLogger();
        start();
    }

    public SchedulerTask runTaskTimer(int delay, Runnable run) {
        val task = new TimerTask(next_id++, delay, run);
        tasks.put(next_id - 1, task);
        return task;
    }

    public SchedulerTask runTaskLater(int delay, Runnable run) {
        val task = new LaterTask(next_id++, delay, run);
        tasks.put(next_id - 1, task);
        return task;
    }

    public SchedulerTask runTask(Runnable run) {
        return runTaskLater(20, run);
    }

    public boolean cancelTask(int id) {
        return (tasks.containsKey(id) && tasks.remove(id).cancel());
    }

    public boolean cancelAllTasks() {
        return new ArrayList<>(tasks.keySet()).stream().noneMatch(this::cancelTask);
    }

    public boolean isRunningTask(int id) {
        return this.tasks.containsKey(id);
    }

    @SneakyThrows
    public void run() {
        while (!isInterrupted()) {
            Thread.sleep(50L);
            for (SchedulerTask task : this.tasks.values()) {
                try {
                    if (task.needUpdate())
                        task.update();
                } catch (Exception e) {
                    this.logger.error("Error while executing task " + task.getId() + ":", e);
                }
            }
        }
    }
}
