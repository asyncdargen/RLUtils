package ru.starfarm.api.binding;

import lombok.Getter;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import ru.starfarm.api.binding.bind.ChatBind;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.util.JsonConfig;

import java.util.ArrayList;
import java.util.List;

public class ChatBinding implements ChatBindingProvider {

    private @Getter List<ChatBind> binds;
    private final JsonConfig<List<ChatBind>> config;

    public ChatBinding(SFUtils mod) {
        config = JsonConfig.<List<ChatBind>>of("binds", new ArrayList<>());
        binds = config.readJson();
        mod.registerEvents(this);
    }

    public ChatBind register(String message, int key, boolean suggest) {
        val bind = new ChatBind(key, message, true, suggest);
        binds.add(bind);
        return bind;
    }

    public void remove(ChatBind bind) {
        if (binds.remove(bind)) update();
    }

    public void update() {
        config.writeJson(binds);
    }

    @SubscribeEvent
    public synchronized void on(InputEvent.KeyInputEvent e) {
        for (ChatBind bind : binds) {
            if (Keyboard.isKeyDown(bind.getKey()) && bind.isEnabled())
                if (bind.isSuggest()) Minecraft.getMinecraft().displayGuiScreen(new GuiChat(format(bind.getMessage())));
                else SFUtils.INSTANCE.getPlayerProvider().sendChatMessage(format(bind.getMessage()));
        }
    }

    private String format(String message) {
        return message;
    }
}
