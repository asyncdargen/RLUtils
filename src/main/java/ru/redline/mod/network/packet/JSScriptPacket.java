package ru.redline.mod.network.packet;

import ru.redline.mod.module.modules.MainModule;

@Packet.Info(id = 2)
public class JSScriptPacket implements Packet {

    public static MainModule module;

    private String name;
    private String code;

    public void handle() {
        if (module == null)
            return;

        if (module.isEnabled())
            module.handleScript(name, code);
    }
}
