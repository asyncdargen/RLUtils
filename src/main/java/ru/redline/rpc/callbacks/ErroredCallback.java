package ru.redline.rpc.callbacks;

import com.sun.jna.Callback;

public interface ErroredCallback extends Callback {
    void apply(int paramInt, String paramString);
}
