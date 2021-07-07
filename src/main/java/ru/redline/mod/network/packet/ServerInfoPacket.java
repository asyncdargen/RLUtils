package ru.redline.mod.network.packet;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import ru.redline.mod.RLUtils;
import ru.redline.mod.session.ServerInfo;

import java.util.ArrayList;
import java.util.List;

@Packet.Info(id = 0)
public class ServerInfoPacket implements Packet {
    private boolean add;

    private String name;

    private String id;

    public void handle() {
        RLUtils.INSTANCE.getPlayerProvider().setServerInfo(this.add ? new ServerInfo(this.id, this.name) : ServerInfo.empty());
        if (this.add) {
            List<String> mods = new ArrayList<>();
            for (ModContainer mod : Loader.instance().getModList()) {
                mods.add("id:" + mod.getModId() + ";name:" + mod.getName() + ";version:" + mod.getVersion());
            }
            RLUtils.INSTANCE.getNetwork().send(new ModListPacket(mods));
            RLUtils.INSTANCE.getPlayerProvider().sendPlugin("rl:mod", new PacketBuffer(Unpooled.buffer()));
        }
    }
}
