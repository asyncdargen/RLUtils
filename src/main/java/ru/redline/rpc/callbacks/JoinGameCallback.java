package ru.redline.rpc.callbacks;

import com.sun.jna.Callback;

public interface JoinGameCallback extends Callback {
    void apply(String paramString);
}
