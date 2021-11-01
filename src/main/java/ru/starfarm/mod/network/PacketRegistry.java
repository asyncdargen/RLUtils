package ru.starfarm.mod.network;

import ru.starfarm.mod.network.packet.Packet;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    private final Map<Integer, Class<? extends Packet>> classByIdRegistry = new HashMap<>();

    private final Map<Class<? extends Packet>, Integer> idByClassRegistry = new HashMap<>();

    public void register(Class<? extends Packet> clazz) {
        Packet.Info info = clazz.getDeclaredAnnotation(Packet.Info.class);
        if (info == null)
            throw new IllegalStateException("packet class must have a @Info annotation");
        if (this.classByIdRegistry.containsKey(info.id()) || this.idByClassRegistry.containsKey(clazz))
            throw new IllegalArgumentException("packet class already registered");
        this.classByIdRegistry.put(info.id(), clazz);
        this.idByClassRegistry.put(clazz, info.id());
    }

    public int idByClass(Class<? extends Packet> clazz) {
        return this.idByClassRegistry.getOrDefault(clazz, -1);
    }

    public Class<? extends Packet> classById(int id) {
        return this.classByIdRegistry.getOrDefault(id, null);
    }
}
