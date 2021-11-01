package ru.starfarm.js.reference.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatReceiveEvent extends ScriptEvent<ClientChatReceivedEvent> {

    public ChatReceiveEvent(ClientChatReceivedEvent original) {
        super(original);
    }

    public String getMessage() {
        return getOriginal().getMessage().getFormattedText();
    }

    public void setMessage(String message) {
        getOriginal().setMessage(new TextComponentString(message));
    }

    public Type getType() {
        return Type.byId(getOriginal().getType().getId());
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        CHAT((byte) 0),
        SYSTEM((byte) 1),
        GAME_INFO((byte) 2);

        private final byte id;

        public static Type byId(byte idIn) {
            for (Type chattype : values()) if (idIn == chattype.id) return chattype;
            return CHAT;
        }
    }
}
