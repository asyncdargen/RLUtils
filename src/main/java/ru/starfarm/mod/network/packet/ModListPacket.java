package ru.starfarm.mod.network.packet;

import lombok.AllArgsConstructor;

import java.util.List;

@Packet.Info(id = 6)
@AllArgsConstructor
public class ModListPacket implements Packet {

    private List<String> mods;

    public void handle() {}

}
