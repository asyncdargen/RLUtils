package ru.redline.rpc.callbacks;

import com.sun.jna.Callback;
import ru.redline.rpc.DiscordUser;

public interface JoinRequestCallback extends Callback {
    void apply(DiscordUser paramDiscordUser);
}
