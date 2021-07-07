package ru.redline.mod.network.packet;

import ru.redline.mod.RLUtils;

@Packet.Info(id = 1)
public class PlayerInfoPacket implements Packet {
    private int coins;

    public void handle() {
        RLUtils.INSTANCE.getPlayerProvider().setCoins(this.coins);
    }
}
