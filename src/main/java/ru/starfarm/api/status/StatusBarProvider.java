package ru.starfarm.api.status;

import ru.starfarm.api.status.bar.Bar;
import ru.starfarm.api.status.style.Style;

public interface StatusBarProvider {
    Bar create(String title, Style style);

    void remove(Bar bar);

    int getStartY();
}
