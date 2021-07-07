package ru.redline.mod.events;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.fml.common.eventhandler.Event;

@AllArgsConstructor @Getter @Setter
public class PluginMessageEvent extends Event {

    private String channel;
    private ByteBuf buffer;

}
