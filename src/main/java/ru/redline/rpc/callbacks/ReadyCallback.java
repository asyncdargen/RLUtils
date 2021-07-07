package ru.redline.rpc.callbacks;

import com.sun.jna.Callback;
import ru.redline.rpc.DiscordUser;

public interface ReadyCallback extends Callback {
    void apply(DiscordUser paramDiscordUser);
}
