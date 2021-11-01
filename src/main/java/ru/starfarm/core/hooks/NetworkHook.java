package ru.starfarm.core.hooks;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.starfarm.core.hook.asm.Hook;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.events.PluginMessageEvent;
import ru.starfarm.util.ReflectUtil;

import java.net.SocketAddress;
import java.util.List;

public class NetworkHook {
    @SideOnly(Side.CLIENT)
    @Hook
    public static void channelActive(NetworkManager nm, ChannelHandlerContext p_channelActive_1_) throws Exception {
        SFUtils.INSTANCE.getNetwork().setNetworkManager(nm);
        ReflectUtil.setValue(nm, ReflectUtil.findField(nm.getClass(), Channel.class), p_channelActive_1_.channel());
        ReflectUtil.setValue(nm, ReflectUtil.findField(nm.getClass(), SocketAddress.class), p_channelActive_1_.channel().remoteAddress());
        try {
            nm.setConnectionState(EnumConnectionState.HANDSHAKING);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (Minecraft.getMinecraft().isSingleplayer())
            return;
        p_channelActive_1_.channel().pipeline().addAfter("decoder", "inDecoder", new MessageToMessageDecoder<Packet<INetHandlerPlayClient>>() {
            protected void decode(ChannelHandlerContext ctx, Packet<INetHandlerPlayClient> msg, List<Object> out) {
                out.add(msg);
                if (msg instanceof SPacketCustomPayload) {
                    SPacketCustomPayload packet = (SPacketCustomPayload) msg;
                    if (packet.getChannelName().contains("rl:packet"))
                        SFUtils.INSTANCE.getNetwork().handle(packet.getBufferData());
                    else MinecraftForge.EVENT_BUS.post(new PluginMessageEvent(packet.getChannelName(), packet.getBufferData()));
                }
            }
        });
    }
}
