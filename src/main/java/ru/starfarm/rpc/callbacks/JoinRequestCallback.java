package ru.starfarm.rpc.callbacks;

import com.sun.jna.Callback;
import ru.starfarm.rpc.DiscordUser;

public interface JoinRequestCallback extends Callback {
    void apply(DiscordUser paramDiscordUser);
}
