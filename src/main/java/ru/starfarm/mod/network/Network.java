package ru.starfarm.mod.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.network.packet.Packet;
import ru.starfarm.util.io.NetUtil;

@Getter
public class Network {
    private final AsyncExecutor EXECUTOR = new AsyncExecutor();
    private final PacketRegistry REGISTRY = new PacketRegistry();
    private final SFUtils mod;
    private NetworkManager nm;

    public Network(SFUtils mod) {
        this.mod = mod;
        this.EXECUTOR.start();
    }

    public void handle(ByteBuf buf) {
        int id = NetUtil.readVarInt(buf);
        if (id < 0)
            return;
        Class<? extends Packet> clazz = this.REGISTRY.classById(id);
        if (clazz == null)
            return;
        Packet packet = NetUtil.readJson(buf, clazz);
        if (packet == null)
            return;
        this.EXECUTOR.post(packet);
    }

    public void send(Packet packet) {
        if (!this.mod.getPlayerProvider().isOnRL())
            return;
        int id = this.REGISTRY.idByClass(packet.getClass());
        if (id < 0) {
            return;
        }
        ByteBuf buf = Unpooled.buffer();
        NetUtil.writeVarInt(id, buf);
        NetUtil.writeJson(buf, packet);
        mod.getPlayerProvider().sendPlugin("sf:packet", new PacketBuffer(buf));
    }

    public void setNetworkManager(NetworkManager nm) {
        this.nm = nm;
    }
}
