package ru.redline.js.reference.event;

import io.netty.buffer.ByteBuf;

public class PluginMessageEvent extends ScriptEvent<ru.redline.mod.events.PluginMessageEvent> {

    public PluginMessageEvent(ru.redline.mod.events.PluginMessageEvent event) {
        super(event);
    }

    public String getChannel() {
        return getOriginal().getChannel();
    }

    public void setChannel(String channel) {
        getOriginal().setChannel(channel);
    }

    public void setBuffer(ByteBuf buf) {
        getOriginal().setBuffer(buf);
    }

    public ByteBuf getBuffer() {
        return getOriginal().getBuffer();
    }

}
