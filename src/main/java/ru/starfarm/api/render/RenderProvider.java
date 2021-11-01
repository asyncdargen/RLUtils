package ru.starfarm.api.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.BiConsumer;

public interface RenderProvider {
    FontRenderer getFontRenderer();

    RenderItem getItemRender();

    ScaledResolution getResolution();

    TextureManager getTextureManager();

//    ResourceLocation getResourceLocation(String url);

    void bindTexture(ResourceLocation paramResourceLocation);

    void drawRect(int x, int y, int weight, int height, int color);

    void drawGradientRect(int x, int y, int weight, int height, int start, int end);

    void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height);

    void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV);

    void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight);

    void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight);

    void drawString(String text, float x, float y, int color);

    void drawCenteredString(String text, float y, int color);

    void drawSplitString(List<String> text, float x, float y, int color);

    void drawSplitString(List<String> text, float x, float y, int color, int split);

    void scaledRunner(double scale, BiConsumer<Double, Double> runner);

    int getStringWidth(String text);

    int getFontHeight();

    int getSplitFontHeight(List<String> text);

    int getSplitFontHeight(List<String> text, int split);

    void clear();

    WorldRender getWorldRender();

}
