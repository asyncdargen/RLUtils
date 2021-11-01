package ru.starfarm.mod.network.packet;

import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.notify.Notify;

@Packet.Info(id = 7)
public class NotifyPacket implements Packet {

    protected String title;
    protected String[] msg;
    protected long duration;
    protected int rgb;

    public void handle() {
        SFUtils.INSTANCE.getNotifies().notify(new Notify(title, msg, duration, rgb));
    }

}
