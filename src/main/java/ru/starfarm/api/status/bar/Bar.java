package ru.starfarm.api.status.bar;

import ru.starfarm.api.status.style.Style;

public interface Bar {
    boolean isVisible();

    Bar setVisible(boolean visible);

    Bar hide();

    Bar show();

    String getName();

    Bar setName(String name);

    Style getStyle();

    Bar setStyle(Style style);

    float getPercent();

    Bar setPercent(float percent);

    int getHeight();

    Bar setHeight(int height);
}
