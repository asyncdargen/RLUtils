package ru.redline.mod.module;

import lombok.Getter;
import ru.redline.mod.RLUtils;

public abstract class Module {
    protected RLUtils mod;

    @Getter
    protected boolean enabled;

    public Module(RLUtils mod) {
        this.mod = mod;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    protected final void enable() {
        onEnable();
        this.enabled = true;
    }

    protected final void disable() {
        onDisable();
        this.enabled = false;
    }
}
