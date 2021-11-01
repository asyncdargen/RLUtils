package ru.starfarm.js.reference.event;

import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ConnectEvent extends ScriptEvent<FMLNetworkEvent.ClientConnectedToServerEvent> {

    public ConnectEvent(FMLNetworkEvent.ClientConnectedToServerEvent original) {
        super(original);
    }

}
