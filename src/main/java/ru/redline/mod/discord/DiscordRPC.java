package ru.redline.mod.discord;

import lombok.Setter;
import lombok.SneakyThrows;
import ru.redline.mod.RLUtils;
import ru.redline.mod.session.PlayerProvider;
import ru.redline.rpc.DiscordEventHandlers;
import ru.redline.rpc.DiscordRichPresence;
import ru.redline.rpc.RPC;
import ru.redline.util.ChatColor;

import java.nio.charset.StandardCharsets;

public class DiscordRPC extends Thread {

    private static final String APP = "850407086340702259";

    private final DiscordEventHandlers handler;
    private final DiscordRichPresence rpc;
    private final PlayerProvider playerProvider;

    @Setter
    private String state = "Загрузка...";

    public DiscordRPC(RLUtils mod) {
        this.playerProvider = mod.getPlayerProvider();
        this.handler = new DiscordEventHandlers();
        RPC.discordInitialize("850407086340702259", this.handler, true);
        this.rpc = new DiscordRichPresence();
        this.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        this.rpc.largeImageKey = "main";
        this.rpc.largeImageText = "RedLine | 1.12.2";
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
