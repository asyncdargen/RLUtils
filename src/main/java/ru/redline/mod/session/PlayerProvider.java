package ru.redline.mod.session;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.Session;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ru.redline.mod.RLUtils;
import ru.redline.util.ChatColor;

@Getter
@Setter
public class PlayerProvider {

    private final Minecraft minecraft;
    private final AltManager altManager;
    private final PlayerController controller;
    private final RLUtils mod;
    private ServerInfo serverInfo;
    private int coins;

    public PlayerProvider(RLUtils mod) {
        this.mod = mod;
        serverInfo = ServerInfo.empty();
        minecraft = mod.getMinecraft();
        altManager = new AltManager(minecraft);
        controller = new PlayerController(minecraft, this);
        mod.registerEvents(this);
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        mod.getRpc().setState(isOnRL() ? ChatColor.stripColor(serverInfo.getName()) : "В меню");
        mod.getModules().refresh();
    }

    public void printChatMessage(String message) {
        this.minecraft.player.sendMessage(new TextComponentString(message));
    }

    public void sendChatMessage(String message) {
        this.minecraft.player.sendChatMessage(message);
    }

    public boolean isOnline() {
        return (this.minecraft.getCurrentServerData() != null && !minecraft.isSingleplayer());
    }

    public boolean isOnRL() {
        return (isOnline() && this.serverInfo.getId().length() > 0);
    }

    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        setServerInfo(ServerInfo.empty());
        mod.getNetwork().setNetworkManager(null);
    }

    @SubscribeEvent
    public void connect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        setServerInfo(ServerInfo.empty());
    }

    public String getName() {
        return altManager.getSession().getUsername();
    }

    public void sendPlugin(String channel, ByteBuf buf) {
        if (mod.getNetwork().getNm() == null) return;
        RLUtils.INSTANCE.getNetwork().getNm().sendPacket(new CPacketCustomPayload(channel, new PacketBuffer(buf)));
    }

}
