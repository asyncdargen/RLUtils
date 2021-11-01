package ru.starfarm.api.binding;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ru.starfarm.api.binding.bind.Bind;
import ru.starfarm.api.binding.bind.Category;
import ru.starfarm.mod.SFUtils;

import java.util.*;

public class KeyBinding implements KeyBindingProvider {

    private final Map<String, Category> categoryMap = new HashMap<>();

    private final SFUtils mod;

    public KeyBinding(SFUtils mod) {
        this.mod = mod;
        mod.registerEvents(this);
    }

    public Category register(Category category) {
        if (isRegistered(category.getId()))
            unregister(category.getId());
        for (Bind b : category.getBinds())
            ClientRegistry.registerKeyBinding(b);
        return categoryMap.put(category.getId(), category);
    }

    public void unregister(String id) {
        List<Bind> binds = categoryMap.remove(id).getBinds();
        mod.getMinecraft().gameSettings.keyBindings
                = (net.minecraft.client.settings.KeyBinding[]) Arrays.stream(mod.getMinecraft().gameSettings.keyBindings)
                .filter(b -> !binds.contains(b)).toArray();
    }

    public boolean isRegistered(String id) {
        return this.categoryMap.containsKey(id);
    }

    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent e) {
        for (Category c : categoryMap.values()) {
            for (Bind b : c.getBinds()) {
                if (b.isPressed()) b.on();
            }
        }
    }
}
