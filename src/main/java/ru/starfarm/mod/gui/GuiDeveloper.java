package ru.starfarm.mod.gui;

import lombok.val;
import net.minecraft.client.gui.GuiScreen;
import ru.starfarm.mod.SFUtils;

public class GuiDeveloper extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        val render = SFUtils.INSTANCE.getRender();
        render.scaledRunner(2.5, (w, h) -> {
            String text = "§cDeleveleloper Mode";
            render.drawString(text, (int) (w / 2 - render.getStringWidth(text) / 2), (int) (h / 2), 0);
            render.clear();
        });
    }
}
