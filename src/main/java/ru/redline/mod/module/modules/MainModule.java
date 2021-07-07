package ru.redline.mod.module.modules;

import io.netty.util.internal.ConcurrentSet;
import lombok.val;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.redline.mod.RLUtils;
import ru.redline.mod.module.Module;
import ru.redline.mod.module.ModuleInfo;
import ru.redline.mod.network.packet.*;
import ru.redline.mod.session.PlayerProvider;

import java.util.Set;

@ModuleInfo(name = "RLUtils", id = "main", packets = {
        PlayerInfoPacket.class,
        ServerInfoPacket.class,
        JSScriptPacket.class,
        BoardAddPacket.class,
        BoardRemovePacket.class,
        BoardUpdatePacket.class,
        NotifyPacket.class,
        ModListPacket.class
})
public class MainModule extends Module {

    private final PlayerProvider playerProvider;
    private final Set<String> activeScripts;

    public MainModule(RLUtils mod) {
        super(mod);
        activeScripts = new ConcurrentSet<>();
        playerProvider = mod.getPlayerProvider();
        JSScriptPacket.module = this;
        mod.registerEvents(this);
    }

    public void onEnable() {
        playerProvider.setCoins(0);
    }

    public void onDisable() {
        for (String name : activeScripts) {
            val script =  mod.getJavaScriptManager().getScript(name);
            if (script != null) script.unload();
        }
        mod.getBoards().getBoards().clear();
    }

    public void handleScript(String name, String code) {
        name = playerProvider.getServerInfo().getId() + "-" + name;
        activeScripts.add(name);
        mod.getJavaScriptManager().loadScript(name, code);
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text e) {
        if ((this.mod.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat || !this.enabled)
            return;
        this.mod.getRender().drawString("§c§lR§f§lC §7§l» §f" + this.playerProvider.getCoins(), 3.0F, (e
                .getResolution().getScaledHeight() - this.mod.getRender().getFontHeight() - 2), -1);
        this.mod.getRender().drawString("§f§lСервер §7§l» §f" + this.playerProvider.getServerInfo().getName(), 3.0F, 3.0F, -1);
    }
}
