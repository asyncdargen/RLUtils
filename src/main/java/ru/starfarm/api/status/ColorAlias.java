package ru.starfarm.api.status;

import net.minecraft.world.BossInfo;
import ru.starfarm.util.Colors;

import java.awt.*;

public enum ColorAlias {
    PINK(BossInfo.Color.PINK, Color.PINK),
    BLUE(BossInfo.Color.BLUE, Colors.of(0, 225, 255, 255)),
    GREEN(BossInfo.Color.GREEN, Color.GREEN),
    RED(BossInfo.Color.RED, Color.RED),
    YELLOW(BossInfo.Color.YELLOW, Color.ORANGE),
    PURPLE(BossInfo.Color.PURPLE, Color.MAGENTA),
    WHITE(BossInfo.Color.WHITE, Color.WHITE);

    private final BossInfo.Color color;

    private final Color alias;

    ColorAlias(BossInfo.Color color, Color alias) {
        this.color = color;
        this.alias = alias;
    }

    public static ColorAlias getByColor(BossInfo.Color color) {
        for (ColorAlias value : values()) {
            if (color == value.color)
                return value;
        }
        return WHITE;
    }

    public Color getAlias() {
        return this.alias;
    }
}
