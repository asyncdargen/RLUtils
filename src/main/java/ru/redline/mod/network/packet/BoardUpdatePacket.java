package ru.redline.mod.network.packet;

import lombok.val;
import ru.redline.mod.RLUtils;
import ru.redline.mod.boards.Content;

import java.util.List;
import java.util.UUID;

@Packet.Info(id = 4)
public class BoardUpdatePacket implements Packet {

    private UUID id;
    private List<Content> contents;

    public void handle() {
        val board =  RLUtils.INSTANCE.getBoards().getBoard(id);
        if (board == null) {
            RLUtils.INSTANCE.getLogger().warn("Board with id " + id + " not exists, BoardUpdatePacket rejected");
            return;
        }

        board.update(contents);
    }
}
