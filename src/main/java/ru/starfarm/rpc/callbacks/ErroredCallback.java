package ru.starfarm.rpc.callbacks;

import com.sun.jna.Callback;

public interface ErroredCallback extends Callback {
    void apply(int paramInt, String paramString);
}
