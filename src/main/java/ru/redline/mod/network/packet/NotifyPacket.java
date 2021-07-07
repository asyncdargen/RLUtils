package ru.redline.mod.network.packet;

import lombok.AllArgsConstructor;
import ru.redline.mod.RLUtils;
import ru.redline.mod.notify.Notify;

@Packet.Info(id = 7)
public class NotifyPacket implements Packet {

    protected String title;
    protected String[] msg;
    protected long duration;
    protected int rgb;

    public void handle() {
        RLUtils.INSTANCE.getNotifies().notify(new Notify(title, msg, duration, rgb));
    }

}
