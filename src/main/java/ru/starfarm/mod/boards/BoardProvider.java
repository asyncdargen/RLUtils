package ru.starfarm.mod.boards;

import java.util.Map;
import java.util.UUID;

public interface BoardProvider {

    Map<UUID, Board> getBoards();

    Board addBoard(UUID uuid, Board board);

    void removeBoard(UUID uuid);

    Board getBoard(UUID uuid);

}
