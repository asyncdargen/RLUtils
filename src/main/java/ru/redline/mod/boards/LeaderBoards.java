package ru.redline.mod.boards;

import lombok.Getter;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.redline.api.render.RenderProvider;
import ru.redline.mod.RLUtils;
import ru.redline.mod.session.PlayerProvider;
import ru.redline.util.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class LeaderBoards implements BoardProvider {

    private Map<UUID, Board> boards = new ConcurrentHashMap<>();
    private RenderProvider render;
    private PlayerProvider playerProvider;

    public LeaderBoards(RLUtils mod) {
        render = mod.getRender();
        playerProvider = mod.getPlayerProvider();
        mod.registerEvents(this);
    }

    public Board addBoard(UUID uuid, Board board) {
        boards.put(uuid, board);
        return board;
    }

    public void removeBoard(UUID uuid) {
        boards.remove(uuid);
    }

    public Board getBoard(UUID uuid) {
        return boards.get(uuid);
    }

    @SubscribeEvent
    public void on(RenderWorldLastEvent e) {
        for (Board board : new ArrayList<>(boards.values())) drawBoard(board, e.getPartialTicks());
    }

    private void drawBoard(Board board, float partialTicks) {
        render.getWorldRender().drawer(
                () -> drawBoardStructure(board), () -> drawBoardContent(board),
                board.getX(), board.getY(), board.getZ(), (float) board.getYaw(), 0);
        render.getWorldRender().drawer(
                () -> drawBoardStructure(board), null,
                board.getX(), board.getY(), board.getZ(), (float) -board.getYaw(), 0);
    }

    final double split = 2;
    final double height = 12;
    final double name = 90;
    final double value = 80;
    final double posit = 18;

    double scale = 1.16;

    final int sb = Colors.rgb(0, 0, 0, (int) (255 * 0.25));

    public void drawBoardStructure(Board board) {
        double x = -((posit + split + name + posit + value) / 2),
               y = -((height + split) * 11 - split);

        for (int i = 0; i < 11; i++) {
            render.getWorldRender().drawRect(x, y + i * (height + split), posit, height, sb);
            render.getWorldRender().drawRect(x + posit + split, y + i * (height + split), name, height, sb);
            render.getWorldRender().drawRect(x + posit + split + name + split, y + i * (height + split), value, height, sb);
        }
    }

    public void drawBoardContent(Board board) {
        double x = -((posit + split + name + posit + value) / 2),
                y = -((height + split) * 11 - split);

        drawCenteredText(board.getPosTitle(), x, y, posit, scale);
        drawCenteredText(board.getNameTitle(), x + posit + split, y, name, scale);
        drawCenteredText(board.getValueTitle(), x + posit + split + name + split, y, value, scale);

        render.scaledRunner(2, (w, h) -> render.drawString(
                board.getTitle(),
                (float) (x / 2d + -x / 2d - render.getStringWidth(board.getTitle()) / 2),
                (float) (y / 2d - render.getFontHeight() / 2d - 5),
                Color.WHITE.getRGB()));

        for (int i = 0; i < (Math.min(board.getContents().size(), 10)); i++) {
            double add = (split + height) * (i + 1);

            Content cn = board.getContents().get(i);

            drawCenteredText(cn.getPosition(), x, y + add, posit, scale);
            drawCenteredText(cn.getName(), x + posit + split, y + add, name, scale);
            drawCenteredText(cn.getValue(), x + posit + split + name + split, y + add, value, scale);
        }
    }

    public void drawCenteredText(String text, double x, double y, double rectWeight, double scale) {
        render.scaledRunner(scale, (w, h) -> {
            render.drawString(
                    text,
                    (float) (x / scale + rectWeight / 2 / scale - render.getStringWidth(text) / 2 / scale),
                    (float) (y / scale + height / 2 / scale - render.getFontHeight() / 2d / scale),
                    Color.WHITE.getRGB());
        });
    }
}
