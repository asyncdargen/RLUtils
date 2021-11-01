package ru.starfarm.mod.gui;

import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Mouse;
import ru.starfarm.util.Colors;

import java.io.IOException;

public class GuiChat extends net.minecraft.client.gui.GuiChat {

    private GuiButton binds;
    private boolean opened;

    public GuiChat(String line) {
        super(line);
    }

    public void initGui() {
        super.initGui();
        val render = Minecraft.getMinecraft().fontRenderer;
        binds = new GuiButton(10, width - 16, height - 12, 14, 12, I18n.format("bind.button")) {
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int rgb = opened ? 255 : 0;
                drawRect(x, y, x + width, y + height, Colors.rgb(rgb, rgb, rgb, hovered ? 80 : 50));
                drawCenteredString(render, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, 14737632);
            }
        };
        inputField.width = width - 20;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(2, height - 14, width - 20, this.height - 2, Integer.MIN_VALUE);
        inputField.drawTextBox();
        ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null)
            handleComponentHover(itextcomponent, mouseX, mouseY);
        binds.drawButton(mc, mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        binds.mousePressed(mc, mouseX, mouseY);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.equals(binds))
            opened = !opened;

    }
}
