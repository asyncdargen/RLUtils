package ru.starfarm.mod.discord;

import lombok.Setter;
import lombok.SneakyThrows;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.session.PlayerProvider;
import ru.starfarm.rpc.DiscordEventHandlers;
import ru.starfarm.rpc.DiscordRichPresence;
import ru.starfarm.rpc.RPC;

import java.nio.charset.StandardCharsets;

public class DiscordRPC extends Thread {

    private static final String APP = "860854785355939880";

    private final DiscordEventHandlers handler;
    private final DiscordRichPresence rpc;
    private final PlayerProvider playerProvider;

    @Setter
    private String state = "Загрузка...";

    public DiscordRPC(SFUtils mod) {
        this.playerProvider = mod.getPlayerProvider();
        this.handler = new DiscordEventHandlers();
        RPC.discordInitialize("860854785355939880", this.handler, true);
        this.rpc = new DiscordRichPresence();
        this.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        this.rpc.largeImageKey = "10";
        this.rpc.largeImageText = "StarFarm | 1.12.2";
        start();
    }

    @SneakyThrows
    public void run() {
        while (!isInterrupted()) {
            RPC.discordRunCallbacks();
            RPC.discordUpdatePresence(update());
            Thread.sleep(2000L);
        }
    }

    public DiscordRichPresence update() {
        rpc.details = new String(playerProvider.getName().getBytes(StandardCharsets.UTF_8));
        rpc.state = new String(state.getBytes(StandardCharsets.UTF_8));
        return rpc;
    }
}
