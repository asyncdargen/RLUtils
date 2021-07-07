package ru.redline.mod.gui;

import lombok.val;
import net.minecraft.client.gui.GuiScreen;
import ru.redline.mod.RLUtils;

public class GuiDeveloper extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        val render = RLUtils.INSTANCE.getRender();
        render.scaledRunner(2.5, (w, h) -> {
            String text = "§cDeveloper Mode";
            render.drawString(text, (int) (w / 2 - render.getStringWidth(text) / 2), (int) (h / 2), 0);
            render.clear();
        });
    }
}
