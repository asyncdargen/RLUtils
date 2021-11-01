package ru.starfarm.api.render;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.starfarm.mod.SFUtils;

import java.util.List;
import java.util.function.BiConsumer;

public class Render implements RenderProvider {

    private final Minecraft minecraft;
    @Getter
    private final WorldRender worldRender;

    public Render(SFUtils mod) {
        this.minecraft = mod.getMinecraft();
        worldRender = new WorldRender(minecraft, this);
    }

    public FontRenderer getFontRenderer() {
        return this.minecraft.fontRenderer;
    }

    public RenderItem getItemRender() {
        return this.minecraft.getRenderItem();
    }

    public ScaledResolution getResolution() {
        return new ScaledResolution(this.minecraft);
    }

    public TextureManager getTextureManager() {
        return this.minecraft.getTextureManager();
    }

    public void bindTexture(ResourceLocation texture) {
        getTextureManager().bindTexture(texture);
    }

    public void clear() {
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void drawRect(int x, int y, int weight, int height, int color) {
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, (y + height), 0.0D).endVertex();
        bufferbuilder.pos((x + weight), (y + height), 0.0D).endVertex();
        bufferbuilder.pos((x + weight), y, 0.0D).endVertex();
        bufferbuilder.pos(x, y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawGradientRect(int x, int y, int weight, int height, int start, int end) {
        float f = (start >> 24 & 0xFF) / 255.0F;
        float f1 = (start >> 16 & 0xFF) / 255.0F;
        float f2 = (start >> 8 & 0xFF) / 255.0F;
        float f3 = (start & 0xFF) / 255.0F;
        float f4 = (end >> 24 & 0xFF) / 255.0F;
        float f5 = (end >> 16 & 0xFF) / 255.0F;
        float f6 = (end >> 8 & 0xFF) / 255.0F;
        float f7 = (end & 0xFF) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((x + weight), y, 0.0D).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(x, y, 0.0D).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(x, (y + height), 0.0D).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((x + weight), (y + height), 0.0D).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), 0).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), 0).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), 0).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), 0).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) uWidth) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public void drawString(String text, float x, float y, int color) {
        getFontRenderer().drawStringWithShadow(text, x, y, color);
    }

    public void scaledRunner(double scale, BiConsumer<Double, Double> runner) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
        ScaledResolution rs = new ScaledResolution(minecraft);
        runner.accept(rs.getScaledWidth() / scale, rs.getScaledHeight() / scale);
        GL11.glPopMatrix();
    }

    public void drawCenteredString(String text, float y, int color) {
        drawString(text, getResolution().getScaledWidth() / 2 - getStringWidth(text) / 2, y, color);
    }

    public void drawSplitString(List<String> text, float x, float y, int color) {
        for (String s : text) {
            drawString(s, x, y, color);
            y += getFontHeight();
        }
    }

    public void drawSplitString(List<String> text, float x, float y, int color, int split) {
        for (String s : text) {
            drawString(s, x, y, color);
            y += (split + getFontHeight());
        }
    }

    public int getStringWidth(String text) {
        return getFontRenderer().getStringWidth(text);
    }

    public int getFontHeight() {
        return (getFontRenderer()).FONT_HEIGHT;
    }

    public int getSplitFontHeight(List<String> text) {
        return text.size() * 9;
    }

    public int getSplitFontHeight(List<String> text, int split) {
        return text.size() * (getFontHeight() + split);
    }
}
