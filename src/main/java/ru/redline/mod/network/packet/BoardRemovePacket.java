package ru.redline.mod.network.packet;

import lombok.val;
import ru.redline.mod.RLUtils;

import java.util.UUID;

@Packet.Info(id = 5)
public class BoardRemovePacket implements Packet {

    private UUID id;

    public void handle() {
        val board =  RLUtils.INSTANCE.getBoards().getBoard(id);
        if (board == null) {
            RLUtils.INSTANCE.getLogger().warn("Board with id " + id + " not exists, BoardRemovePacket rejected");
            return;
        }

        RLUtils.INSTANCE.getBoards().removeBoard(id);
    }
}
