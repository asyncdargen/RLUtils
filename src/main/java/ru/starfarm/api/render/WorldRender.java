package ru.starfarm.api.render;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static net.minecraft.client.renderer.GlStateManager.*;
import static net.minecraft.client.renderer.GlStateManager.popMatrix;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WorldRender {

    private final Minecraft mc;
    private final RenderProvider render;

    public void drawer(Runnable any, Runnable text, double x, double y, double z, float yaw, float pitch) {
        float partialTicks = mc.getRenderPartialTicks();
        val p = mc.player;
        if (p == null) return;
        pushMatrix();
        translate(
                -p.lastTickPosX - (p.posX - p.lastTickPosX) * partialTicks + x,
                -p.lastTickPosY - (p.posY - p.lastTickPosY) * partialTicks + y,
                -p.lastTickPosZ - (p.posZ - p.lastTickPosZ) * partialTicks + z
        );
        glNormal3f(0, 1f, 0);
        rotate(yaw, 0, 1f, 0);
        rotate(pitch, 1f, 0, 0);
        scale(-0.025F, -0.025F, 0.025F);
        disableLighting();
        depthMask(false);
        enableBlend();
        tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        disableTexture2D();
        if (any != null) any.run();
        enableTexture2D();
        if (text != null) text.run();
        enableDepth();
        depthMask(true);
        enableLighting();
        disableBlend();
        render.clear();
        popMatrix();
    }

    public void drawString(String text, double x, double y, double z, float yaw, float pitch, int color) {
        drawer(null, () -> {
            render.drawString(text, 0, 0, color);
        }, x, y, z, yaw, pitch);
    }

    public void drawString(String text, double x, double y, double z, float yaw, float pitch, int color, double scale) {
        drawer(null, () -> {
            pushMatrix();
            scale(scale, scale, scale);
            render.drawString(text, 0, 0, color);
            popMatrix();
        }, x, y, z, yaw, pitch);
    }

    /*
     * Use only in drawer, because method call artifacts or crash game
     */
    public void drawRect(double x, double y, double z, float yaw, float pitch, double weight, double height, int color) {
        drawer(() -> drawRect(0, 0, weight, height, color), null, x, y, z, yaw, pitch);
    }

    public void drawCenterRect(double x, double y, double z, float yaw, float pitch, double weight, double height, int color) {
        drawer(() -> drawCenterRect(weight, height, color), null, x, y, z, yaw, pitch);
    }

    public void drawRect(double x, double y, double weight, double height, int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (y + height), 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos((x + weight), (y + height), 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos((x + weight), y, 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos(x, y, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    /*
     * Use only in drawer, because method call artifacts or crash game
     */
    public void drawCenterRect(double weight, double height, int color) {
        double x = -weight / 2d;
        double y = -height / 2d;
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (y + height), 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos((x + weight), (y + height), 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos((x + weight), y, 0.0D).color(r, g, b, a).endVertex();
        bufferbuilder.pos(x, y, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
    }



}
