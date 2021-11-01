package ru.starfarm.mod.gui;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import ru.starfarm.api.render.RenderProvider;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.gui.element.GuiPasswordField;
import ru.starfarm.util.ReflectUtil;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class GuiAltManager extends GuiScreen {

    private final GuiScreen parent;
    private final RenderProvider render = SFUtils.INSTANCE.getRender();
    private GuiPasswordField passwordField;
    private GuiTextField userField;
    private boolean enabled = true;

    public void initGui() {
        userField = new GuiTextField(3, mc.fontRenderer, width / 2 - 100, 66, 200, 20);
        passwordField = new GuiPasswordField(width / 2 - 100, 106, 200, 20);
        buttonList.add(new GuiButton(0, width / 2 - 101, height / 2 + 10, 100, 20, I18n.format("session.alts.btn.cancel")));
        buttonList.add(new GuiButton(1, width / 2 + 1, height / 2 + 10, 100, 20, I18n.format("session.alts.btn.done")));
        buttonList.add(new GuiButton(2, width / 2 - 50, height / 2 + 35, 100, 20, I18n.format("session.alts.btn.restore")));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        buttonList.get(1).enabled = userField.getText().length() > 0 && enabled;
        buttonList.get(0).enabled = enabled;
        buttonList.get(2).enabled = enabled;

        passwordField.setEnabled(enabled);
        userField.setEnabled(enabled);

        drawDefaultBackground();
        render.drawCenteredString(I18n.format("session.alts"), 20, 16777215);
        render.drawCenteredString(getSessionInfo(mc.getSession()), 31, 16777215);
        render.drawString("§7" + I18n.format("session.alts.field.login"), width / 2 - 100, 53, 0);
        render.drawString("§7" + I18n.format("session.alts.field.password"), width / 2 - 100, 93, 0);
        if (!enabled)
            render.drawString("§7" + I18n.format("session.alts.state.wait"), width / 2 + 2, height / 2, 0);
        passwordField.drawTextBox();
        userField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) mc.displayGuiScreen(parent);
        else {
            passwordField.textboxKeyTyped(typedChar, keyCode);
            userField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        userField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (!enabled) return;
        switch (button.id) {
            case 0: keyTyped('1', 1); break;
            case 1: applySession(); break;
            case 2: SFUtils.INSTANCE.getPlayerProvider().getAltManager().restoreDefault();
        }
    }

    private String getSessionInfo(Session session) {
        boolean legacy = ReflectUtil.getFieldValue(session, ReflectUtil.findField(Session.class, Session.Type.class)) == Session.Type.LEGACY;
        return String.format(
                "§7%s (%s§7)", session.getUsername(),
                legacy ? "§c" + I18n.format("session.alts.state.offline")
                        : "§2" + I18n.format("session.alts.state.online")
        );
    }

    private void applySession() {
        String login = userField.getText(), password = passwordField.getText();
        CompletableFuture.runAsync(() -> {
            enabled = false;
            try {
                SFUtils.INSTANCE.getPlayerProvider().getAltManager().login(login, password);
            } catch (Exception e) {
                SFUtils.INSTANCE.getLogger().info("Error while login : " + e.getMessage());
            }
            enabled = true;
        });
        userField.setText("");
        passwordField.setText("");
    }
}
