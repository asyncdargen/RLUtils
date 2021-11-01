package ru.starfarm.rpc.callbacks;

import com.sun.jna.Callback;

public interface JoinGameCallback extends Callback {
    void apply(String paramString);
}
