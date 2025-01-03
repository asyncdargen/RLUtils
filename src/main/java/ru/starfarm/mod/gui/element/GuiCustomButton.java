package ru.starfarm.mod.gui.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ru.starfarm.api.render.RenderProvider;
import ru.starfarm.mod.SFUtils;

import java.awt.*;

public class GuiCustomButton extends GuiButton {

    public Type type;
    public RenderProvider render;

    public GuiCustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Type type) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        render = SFUtils.INSTANCE.getRender();
        this.type = type;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            render.bindTexture(type.getResource());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            int x = this.x + (hovered ? 1 : 0),
                    y = this.y + (hovered ? 1 : 0);
            render.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
            mouseDragged(mc, mouseX, mouseY);
            drawCenteredString(render.getFontRenderer(), this.displayString, x + this.width / 2, y + (this.height - 8) / 2, Color.white.getRGB());
        }
    }

    @AllArgsConstructor @Getter
    public enum Type {
        GRAY(new ResourceLocation("sfutils:textures/gui/button/gray.png")),
        RED(new ResourceLocation("sfutils:textures/gui/button/red.png"));

        ResourceLocation resource;
    }
}
