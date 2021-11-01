package ru.starfarm.mod.network.packet;

import ru.starfarm.mod.SFUtils;

@Packet.Info(id = 1)
public class PlayerInfoPacket implements Packet {
    private int coins;

    public void handle() {
        SFUtils.INSTANCE.getPlayerProvider().setCoins(this.coins);
    }
}
