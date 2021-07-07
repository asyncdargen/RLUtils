package ru.redline.api.status;

import ru.redline.api.status.bar.Bar;
import ru.redline.api.status.style.Style;

public interface StatusBarProvider {
    Bar create(String title, Style style);

    void remove(Bar bar);

    int getStartY();
}
