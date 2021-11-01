package ru.starfarm.rpc.callbacks;

import com.sun.jna.Callback;

public interface DisconnectedCallback extends Callback {
    void apply(int paramInt, String paramString);
}
