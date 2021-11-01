package ru.starfarm.mod.module.modules;

import lombok.Data;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.starfarm.api.render.RenderProvider;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.module.Module;
import ru.starfarm.mod.module.ModuleInfo;
import ru.starfarm.mod.session.PlayerProvider;
import ru.starfarm.util.Colors;

import java.awt.*;

@ModuleInfo(name = "AzerusUtils", id = "azerus", server = "azerus", packets = {})
public class AzerusModule extends Module {
    public static AzerusData data;

    private final RenderProvider render;

    private final PlayerProvider player;

    public AzerusModule(SFUtils mod) {
        super(mod);
        this.render = mod.getRender();
        this.player = mod.getPlayerProvider();
        data = new AzerusData();
        mod.registerEvents(this);
    }

    public void onEnable() {
        data = new AzerusData();
    }

    public void onDisable() {
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text e) {
        if (!this.enabled)
            return;
        double hp = data.health / data.maxHealth;
        double exp = (data.level >= data.maxHealth) ? 1.0D : (data.exp / data.needExp);
        int hpp = (int) (80.0D * hp), expp = (int) (80.0D * hp);
        this.render.drawRect(45, 20, 80, 10, Colors.rgb(64, 64, 64));
        this.render.drawRect(45, 30, hpp, 10, Colors.rgb(117, 31, 35, 185));
        this.render.drawRect(45, 40, expp, 10, Colors.rgb(51, 60, 117));
        this.render.drawRect(45 + hpp, 30, 80 - hpp, 10, Colors.rgb(0, 0, 0, 120));
        this.render.drawRect(45 + expp, 40, 80 - expp, 10, Colors.rgb(0, 0, 0, 120));
        this.render.drawString(this.player.getName(), 47.0F, 21.0F, Color.WHITE.getRGB());
        this.render.drawString("HP " + data.health + "/" + data.maxHealth, 47.0F, 31.0F, Color.WHITE.getRGB());
        this.render.drawString("XP " + ((data.level >= data.maxLevel) ? "Макс. уровень" : ((int) data.exp + "/" + (int) data.needExp)), 47.0F, 41.0F, Color.WHITE
                .getRGB());
    }

    @Data
    static class AzerusData {
        int maxLevel = 1;
        int level;
        int health;
        int maxHealth = 1;
        double exp;
        double needExp = 1.0D;
    }
}
