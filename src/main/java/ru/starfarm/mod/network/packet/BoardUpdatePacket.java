package ru.starfarm.mod.network.packet;

import lombok.val;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.boards.Content;

import java.util.List;
import java.util.UUID;

@Packet.Info(id = 4)
public class BoardUpdatePacket implements Packet {

    private UUID id;
    private List<Content> contents;

    public void handle() {
        val board =  SFUtils.INSTANCE.getBoards().getBoard(id);
        if (board == null) {
            SFUtils.INSTANCE.getLogger().warn("Board with id " + id + " not exists, BoardUpdatePacket rejected");
            return;
        }

        board.update(contents);
    }
}
