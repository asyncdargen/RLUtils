package ru.starfarm.mod.module;

import lombok.Getter;
import ru.starfarm.mod.SFUtils;

public abstract class Module {
    protected SFUtils mod;

    @Getter
    protected boolean enabled;

    public Module(SFUtils mod) {
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
