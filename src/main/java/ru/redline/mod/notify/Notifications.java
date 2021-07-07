package ru.redline.mod.notify;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ru.redline.api.render.RenderProvider;
import ru.redline.mod.RLUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Notifications implements NotificationProvider {
    private final int SPLIT_Y = 7, SPLIT_X = 7, W_SIZE = 120;

    private final int BG = -13068858, PBG = -15049344;

    private final int WHITE = Color.WHITE

            .getRGB();

    private final List<Notify> notifies = new LinkedList<>();

    private final RenderProvider render;

    private final RLUtils mod;

    public Notifications(RLUtils mod) {
        this.mod = mod;
        this.render = mod.getRender();
        mod.registerEvents(this);
    }

    public void notify(Notify n) {
        n.x = this.render.getResolution().getScaledWidth();
        n.y = 7;
        this.notifies.add(n);
    }

    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.notifies.clear();
    }

    @SubscribeEvent
    public void connect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        this.notifies.clear();
    }

    public void remove(Notify notify) {
        this.notifies.remove(notify);
    }

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Text e) {
        ScaledResolution rs = this.render.getResolution();
        int y = 7;
        int w = rs.getScaledWidth();
        for (int i = 0; i < this.notifies.size(); i++) {
            Notify n = this.notifies.get(i);
            int x = Math.min(w - 120 - 7, w - 11 - this.render
                    .getStringWidth(n.title) - 7);
            int size = Math.max(120, 11 + this.render.getStringWidth(n.title));
            for (String s : n.msg) {
                x = Math.min(x, w - 16 - this.render.getStringWidth(s) - 7);
                size = Math.max(16 + this.render.getStringWidth(s), size);
            }
            if (!n.enabled && (n.x -= 6) <= x) {
                n.x = x;
                n.enabled = true;
            }
            if (n.expired() && (n.x += 4) >= w) {
                this.notifies.remove(n);
            } else {
                if (n.y < y && (n.y += Math.min(3, y - n.y)) > y)
                    n.y = y;
                if (n.y > y && (n.y -= Math.min(3, n.y - y)) < y)
                    n.y = y;
                int height = 16 + this.render.getSplitFontHeight(Arrays.asList(n.msg));
                this.render.drawRect(n.x, n.y, size, height, -13068858);
                this.render.drawRect(n.x, n.y, 4, height, n.rgb);
                this.render.drawRect(n.x + 4, n.y, (int) (size * n.percent()), 4, -15049344);
                this.render.drawString(n.title, (n.x + 10), (n.y + 6), this.WHITE);
                this.render.drawSplitString(Arrays.asList(n.msg), (n.x + 14), (n.y + 14), this.WHITE);
                y += 7 + height;
            }
        }
    }
}
