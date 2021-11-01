package ru.starfarm.rpc.callbacks;

import com.sun.jna.Callback;

public interface SpectateGameCallback extends Callback {
    void apply(String paramString);
}
