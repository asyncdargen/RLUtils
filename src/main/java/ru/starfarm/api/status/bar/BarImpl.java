package ru.starfarm.api.status.bar;

import ru.starfarm.api.status.style.Style;

public class BarImpl implements Bar {
    private static final int defHeight = 5;

    private boolean visible;

    private String name;

    private float percent;

    private Style style;

    private int height;

    public BarImpl(String name, Style style) {
        this.visible = true;
        this.name = name;
        this.percent = 1.0F;
        this.style = style;
        this.height = 5;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public BarImpl setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public BarImpl setName(String name) {
        this.name = name;
        return this;
    }

    public float getPercent() {
        return this.percent;
    }

    public Style getStyle() {
        return this.style;
    }

    public BarImpl setStyle(Style style) {
        this.style = style;
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public BarImpl setHeight(int height) {
        this.height = height;
        return this;
    }

    public Bar hide() {
        this.visible = false;
        return this;
    }

    public Bar show() {
        this.visible = true;
        return this;
    }

    public Bar setPercent(float percent) {
        this.percent = (percent > 1.0F || percent < 0.0F) ? 1.0F : percent;
        return this;
    }
}
