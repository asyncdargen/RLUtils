package ru.redline.mod.network.packet;

import lombok.val;
import ru.redline.mod.RLUtils;
import ru.redline.mod.boards.Board;

import java.util.UUID;

@Packet.Info(id = 3)
public class BoardAddPacket implements Packet {

    private UUID id;
    private Board board;

    public void handle() {
        val board =  RLUtils.INSTANCE.getBoards().getBoard(id);
        if (board != null) {
            RLUtils.INSTANCE.getLogger().warn("Board with id " + id + " already exists, BoardAddPacket rejected");
            return;
        }

        RLUtils.INSTANCE.getBoards().addBoard(id, this.board);
    }
}
