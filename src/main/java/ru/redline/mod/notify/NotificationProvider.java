package ru.redline.mod.notify;

public interface NotificationProvider {
    void notify(Notify paramNotify);

    void remove(Notify paramNotify);
}
