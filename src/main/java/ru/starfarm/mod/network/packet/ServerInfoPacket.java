package ru.starfarm.mod.network.packet;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.session.ServerInfo;

import java.util.ArrayList;
import java.util.List;

@Packet.Info(id = 0)
public class ServerInfoPacket implements Packet {
    private boolean add;

    private String name;

    private String id;

    public void handle() {
        SFUtils.INSTANCE.getPlayerProvider().setServerInfo(this.add ? new ServerInfo(this.id, this.name) : ServerInfo.empty());
        if (this.add) {
            List<String> mods = new ArrayList<>();
            for (ModContainer mod : Loader.instance().getModList()) {
                mods.add("id:" + mod.getModId() + ";name:" + mod.getName() + ";version:" + mod.getVersion());
            }
            SFUtils.INSTANCE.getNetwork().send(new ModListPacket(mods));
            SFUtils.INSTANCE.getPlayerProvider().sendPlugin("sf:mod", new PacketBuffer(Unpooled.buffer()));
        }
    }
}
