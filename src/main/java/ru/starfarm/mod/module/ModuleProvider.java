package ru.starfarm.mod.module;

import java.util.List;
import java.util.Map;

public interface ModuleProvider {
    void registerModule(Class<? extends Module> paramClass);

    void unregisterModule(Class<? extends Module> paramClass);

    List<ModuleInfo> getActiveModules();

    Map<ModuleInfo, Module> getModules();

    void refresh();
}
