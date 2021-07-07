package ru.redline.js.reference.event;

import net.minecraftforge.client.event.ClientChatEvent;

public class ChatSendEvent extends ScriptEvent<ClientChatEvent> {

    public ChatSendEvent(ClientChatEvent original) {
        super(original);
    }

    public String getMessage()
    {
        return getOriginal().getMessage();
    }

    public void setMessage(String message)
    {
        getOriginal().setMessage(message);
    }

    public String getOriginalMessage()
    {
        return getOriginal().getOriginalMessage();
    }

    public boolean isCommand() {
        return getMessage().startsWith("/");
    }

}
