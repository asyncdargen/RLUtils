package ru.redline.js.reference.event;

import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class DisconnectEvent extends ScriptEvent<FMLNetworkEvent.ClientDisconnectionFromServerEvent> {

    public DisconnectEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent original) {
        super(original);
    }

}
