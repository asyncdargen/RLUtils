package ru.starfarm.mod.network.packet;

import lombok.val;
import ru.starfarm.mod.SFUtils;

import java.util.UUID;

@Packet.Info(id = 5)
public class BoardRemovePacket implements Packet {

    private UUID id;

    public void handle() {
        val board =  SFUtils.INSTANCE.getBoards().getBoard(id);
        if (board == null) {
            SFUtils.INSTANCE.getLogger().warn("Board with id " + id + " not exists, BoardRemovePacket rejected");
            return;
        }

        SFUtils.INSTANCE.getBoards().removeBoard(id);
    }
}
