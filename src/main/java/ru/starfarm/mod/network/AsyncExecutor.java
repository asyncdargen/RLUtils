package ru.starfarm.mod.network;

import lombok.SneakyThrows;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.network.packet.Packet;

import java.util.Deque;
import java.util.LinkedList;

public class AsyncExecutor {
    private final Deque<Packet> queue = new LinkedList<>();

    private final Thread executor;

    public AsyncExecutor() {
        this.executor = new Thread(new Runnable() {
            @SneakyThrows
            public void run() {
                while (!AsyncExecutor.this.executor.isInterrupted()) {
                    Packet packet = AsyncExecutor.this.queue.poll();
                    if (packet == null) {
                        Thread.sleep(5L);
                        continue;
                    }
                    try {
                        packet.handle();
                    } catch (Exception e) {
                        SFUtils.INSTANCE.getLogger().error("Error while handling packet : " + packet, e);
                        Thread.sleep(5L);
                    }
                }
            }
        });
    }

    public void post(Packet packet) {
        this.queue.addLast(packet);
    }

    public void postWithPriority(Packet packet) {
        this.queue.addFirst(packet);
    }

    public void stop() {
        this.executor.interrupt();
        if (this.executor.isAlive())
            this.executor.stop();
        this.queue.clear();
    }

    public void start() {
        this.executor.start();
    }
}
