package ru.redline.mod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import ru.redline.mod.RLUtils;
import ru.redline.util.ReflectUtil;

import java.io.IOException;

public class GuiMultiplayer extends net.minecraft.client.gui.GuiMultiplayer {

    private static final ReflectUtil.FieldAccessor<Session.Type> sessionType
            = ReflectUtil.fieldAccessor(ReflectUtil.findField(Session.class, Session.Type.class));

    public GuiMultiplayer(GuiScreen parentScreen) {
        super(parentScreen);
    }

    public void createButtons() {
        super.createButtons();
        buttonList.add(new GuiButton(20, width / 2 - 50, 12, 100, 20, I18n.format("session.alts")));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RLUtils.INSTANCE.getRender().drawCenteredString(getSessionInfo(mc.getSession()), 3, 0);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 20) mc.displayGuiScreen(new GuiAltManager(this));
        else super.actionPerformed(button);
    }

    private String getSessionInfo(Session session) {
        return String.format(
                "§7%s (%s§7)", session.getUsername(), sessionType.get(session) == Session.Type.LEGACY
                        ? "§c" + I18n.format("session.alts.state.offline")
                        : "§2" + I18n.format("session.alts.state.online")
        );
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_DELETE)
            super.actionPerformed(new GuiButton(2, 0, 0, 0, 0, ""));
        else super.keyTyped(typedChar, keyCode);
    }
}
