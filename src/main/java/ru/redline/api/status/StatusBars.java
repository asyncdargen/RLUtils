package ru.redline.api.status;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ru.redline.api.render.RenderProvider;
import ru.redline.api.status.bar.Bar;
import ru.redline.api.status.bar.BarImpl;
import ru.redline.api.status.style.Style;
import ru.redline.mod.RLUtils;
import ru.redline.util.Colors;
import ru.redline.util.ReflectUtil;

import java.util.*;

public class StatusBars implements StatusBarProvider {
    private final List<Bar> bars;
    private final RenderProvider render;
    private static final ReflectUtil.FieldAccessor<Map<UUID, BossInfoClient>> barsMap
            = ReflectUtil.fieldAccessor(ReflectUtil.findField(GuiBossOverlay.class, Map.class));
    private int y = 0;

    public StatusBars(RLUtils mod) {
        this.render = mod.getRender();
        this.bars = new LinkedList<>();
        mod.registerEvents(this);
    }

    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.bars.clear();
    }

    @SubscribeEvent
    public void overlay(RenderGameOverlayEvent.BossInfo e) {
        e.setCanceled(true);
    }

    private Collection<BossInfoClient> getBars() {
        try {
            return barsMap.get(Minecraft.getMinecraft().ingameGUI.getBossOverlay()).values();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Bar create(String name, Style style) {
        BarImpl barImpl = new BarImpl(name, style);
        this.bars.add(barImpl);
        return barImpl;
    }

    public void remove(Bar bar) {
        this.bars.remove(bar);
    }

    public int getStartY() {
        return this.y;
    }

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Text e) {
        ScaledResolution rs = e.getResolution();
        this.y = -5;
        int startX = rs.getScaledWidth() / 2 - 91;
        for (BossInfoClient b : getBars())
            drawBar(startX, b.getPercent(), 5, b.getName().getFormattedText(),
                    ColorAlias.getByColor(b.getColor()).getAlias().getRGB(), Colors.rgb(153, 151, 151, 200));
        for (Bar bar : this.bars)
            if (bar.isVisible()) drawBar(startX, bar.getPercent(), bar.getHeight(), bar.getName(),
                    bar.getStyle().getColor().getRGB(), bar.getStyle().getBackground().getRGB());
    }

    public void drawBar(int x, float percent, int height, String name, int color, int bg) {
        this.y += this.render.getFontHeight() + 3;
        this.render.drawCenteredString(name, this.y, -1);
        this.y += height + 5;
        this.render.drawRect(x, this.y, 182, height, bg);
        this.render.drawRect(x, this.y, (int) (182.0F * percent), height, color);
    }
}
