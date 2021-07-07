package ru.redline.mod.module;

import ru.redline.mod.RLUtils;
import ru.redline.mod.network.packet.Packet;
import ru.redline.mod.session.PlayerProvider;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

public class Modules implements ModuleProvider {
    private final Map<ModuleInfo, Module> modules = new HashMap<>();
    private final List<ModuleInfo> activeModules = new ArrayList<>();
    private final RLUtils mod;
    private final PlayerProvider playerProvider;

    public Modules(RLUtils mod) {
        this.mod = mod;
        this.playerProvider = mod.getPlayerProvider();
    }

    public Map<ModuleInfo, Module> getModules() {
        return this.modules;
    }

    public List<ModuleInfo> getActiveModules() {
        return this.activeModules;
    }

    public void registerModule(Class<? extends Module> clazz) {
        ModuleInfo info = clazz.getDeclaredAnnotation(ModuleInfo.class);
        Objects.requireNonNull(info, "module must annotated ModuleInfo");
        Module module = null;
        try {
            module = (Module) MethodHandles.lookup()
                    .findConstructor(clazz, MethodType.methodType(void.class, RLUtils.class))
                    .invoke(mod);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(module, "error while create new module instance");
        for (Class<? extends Packet> packet : info.packets())
            this.mod.getNetwork().getREGISTRY().register(packet);
        this.modules.put(info, module);
    }

    public void unregisterModule(Class<? extends Module> clazz) {
        ModuleInfo info = clazz.getDeclaredAnnotation(ModuleInfo.class);
        Objects.requireNonNull(info, "module must annotated ModuleInfo");
        Module module = modules.get(info);
        Objects.requireNonNull(module, "module must registered");
        if (activeModules.contains(info))
            module.disable();
        modules.remove(info);
    }

    public void refresh() {
        String server = this.playerProvider.getServerInfo().getId();
        for (ModuleInfo info : this.modules.keySet()) {
            if (this.activeModules.contains(info)) {
                this.modules.get(info).disable();
                this.activeModules.remove(info);
                this.mod.getLogger().info("Disable module " + info.name() + " (" + info.id() + ")");
            }
        }
        if (!this.playerProvider.isOnRL())
            return;
        this.mod.getLogger().info("Refresh modules with server id '" + server + "'...");
        for (ModuleInfo info : this.modules.keySet()) {
            if (server.startsWith(info.server())) {
                this.modules.get(info).enable();
                this.activeModules.add(info);
                this.mod.getLogger().info("Enable module " + info.name() + " (" + info.id() + ")");
            }
        }
    }
}
