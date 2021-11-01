package ru.starfarm.mod.network.packet;

import lombok.val;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.boards.Board;

import java.util.UUID;

@Packet.Info(id = 3)
public class BoardAddPacket implements Packet {

    private UUID id;
    private Board board;

    public void handle() {
        val board =  SFUtils.INSTANCE.getBoards().getBoard(id);
        if (board != null) {
            SFUtils.INSTANCE.getLogger().warn("Board with id " + id + " already exists, BoardAddPacket rejected");
            return;
        }

        SFUtils.INSTANCE.getBoards().addBoard(id, this.board);
    }
}
