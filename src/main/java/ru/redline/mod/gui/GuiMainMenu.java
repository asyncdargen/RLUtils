package ru.redline.mod.gui;

import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import ru.redline.api.render.RenderProvider;
import ru.redline.mod.RLUtils;
import ru.redline.mod.gui.element.GuiCustomButton;

import java.io.IOException;

public class GuiMainMenu extends GuiScreen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation("rlutils:textures/gui/main/bg.png");
    private static final ResourceLocation MOVEDAUN = new ResourceLocation("rlutils:textures/gui/main/movedaun.png");
    private static final ResourceLocation LOGO = new ResourceLocation("rlutils:textures/gui/logo/logo530.png");

    private static final RenderProvider render = RLUtils.INSTANCE.getRender();

    private static final double scale = 1.25;

    public void initGui() {
        FMLClientHandler.instance().setupServerList();
        int xc = width / 2, yc = height / 2 - 20;
        int w = (int) (100 * scale), h = (int) (25 * scale);
        buttonList.add(new GuiButton(0, xc - 40, (yc + 20) - (yc + 20) / 2 - 40, 80, 80, ""){
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                render.bindTexture(LOGO);
                glPreTexture();
                hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                render.drawModalRectWithCustomSizedTexture(x + (hovered ? 1 : 0), y + (hovered ? 1 : 0), 0, 0, width, height, width, height);
            }
        });
        buttonList.add(new GuiCustomButton(1, xc - w / 2, yc, w, h, I18n.format("gui.main.single"), GuiCustomButton.Type.RED));
        buttonList.add(new GuiCustomButton(2, xc - w / 2, yc + (h + 5) * 1, w, h, I18n.format("gui.main.multi"), GuiCustomButton.Type.RED));
        buttonList.add(new GuiCustomButton(3, xc - w / 2, yc + (h + 5) * 2, w, h, I18n.format("gui.main.settings"), GuiCustomButton.Type.RED));
        buttonList.add(new GuiCustomButton(4, xc - w / 2, yc + (h + 5) * 3 + 6, w, h, I18n.format("gui.main.quit"), GuiCustomButton.Type.GRAY));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground();
        drawMoveDaun(mouseX);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton btn) throws IOException {
        super.actionPerformed(btn);
        switch (btn.id) {
            case 0:
                if (RLUtils.developer && GuiScreen.isShiftKeyDown()) mc.displayGuiScreen(new GuiDeveloper());
                else FMLClientHandler.instance().connectToServer(new GuiMainMenu(), new ServerData("RedLine", "51.77.56.47:25565", false));
                break;
            case 1: mc.displayGuiScreen(new GuiWorldSelection(this)); break;
            case 2: mc.displayGuiScreen(new GuiMultiplayer(this)); break;
            case 3: mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)); break;
            case 4: mc.shutdown(); break;
        }
    }

    public void drawBackground() {
        render.bindTexture(BACKGROUND);
        glPreTexture();
        render.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);
    }

    public void drawMoveDaun(int mouseX) {
        render.bindTexture(MOVEDAUN);
        glPreTexture();
        render.drawModalRectWithCustomSizedTexture((int) (200.0D - 200.0D * (mouseX / (double) width)), 0, 0, 0, width, height, width, height);
    }

    private void glPreTexture() {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

}
