package ru.redline.mod.session;

import lombok.Data;

@Data
public class ServerInfo {

    private final String id;
    private final String name;

    public static ServerInfo empty() {
        return new ServerInfo("", "");
    }

}
