package ru.redline.mod.notify;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Notify {

    protected String title;
    protected String[] msg;
    protected long duration;
    protected int rgb;
    protected int x;
    protected int y;
    protected boolean enabled;
    protected long out;

    public Notify(String title, String[] msg, long duration, int rgb) {
        this.title = title;
        this.msg = msg;
        this.duration = duration;
        this.rgb = rgb;
        this.out = duration + System.currentTimeMillis();
    }

    public boolean expired() {
        return (this.out < System.currentTimeMillis());
    }

    public double percent() {
        return expired() ? 0.0D : ((this.out - System.currentTimeMillis()) / (double) this.duration);
    }
}
