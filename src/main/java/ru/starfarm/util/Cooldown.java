package ru.starfarm.util;

public class Cooldown {
    protected final int delay;
    protected final boolean autoUpd;
    protected long stop;

    public Cooldown(int delay) {
        this(delay, false);
    }

    public Cooldown(int delay, boolean autoUpd) {
        this.delay = delay;
        this.autoUpd = autoUpd;
        restart();
    }

    public long restart() {
        return this.stop = System.currentTimeMillis() + (this.delay * 50);
    }

    public boolean is() {
        if (this.stop <= System.currentTimeMillis()) {
            if (this.autoUpd)
                restart();
            return true;
        }
        return false;
    }
}
