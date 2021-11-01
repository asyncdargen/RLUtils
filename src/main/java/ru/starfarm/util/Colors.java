package ru.starfarm.util;

import java.awt.*;

public class Colors {
    public static Color of(int rgb) {
        return new Color(rgb);
    }

    public static Color of(int r, int g, int b, int a) {
        return new Color(r, g, b, a);
    }

    public static Color of(int r, int g, int b) {
        return new Color(r, g, b);
    }

    public static int rgb(int r, int g, int b) {
        return of(r, g, b).getRGB();
    }

    public static int rgb(int r, int g, int b, int a) {
        return of(r, g, b, a).getRGB();
    }
}
