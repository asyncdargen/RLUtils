package ru.redline.core;

import ru.redline.core.hook.minecraft.HookLoader;

public class RLCore extends HookLoader {

    protected void registerHooks() {
        registerHookContainer("ru.redline.core.hooks.ScoreHook");
        registerHookContainer("ru.redline.core.hooks.NetworkHook");
        registerHookContainer("ru.redline.core.hooks.SessionHook");
        registerHookContainer("ru.redline.core.hooks.BrandHook");
    }

}
