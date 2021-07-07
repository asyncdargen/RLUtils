package ru.redline.api.binding.bind;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String id;

    private final List<Bind> binds;

    public Category(String id) {
        this.id = id;
        this.binds = new ArrayList<>();
    }

    public Category add(String id, int keyCode, Runnable click) {
        this.binds.add(new Bind(id, keyCode, this.id, click));
        return this;
    }

    public List<Bind> getBinds() {
        return this.binds;
    }

    public String getId() {
        return this.id;
    }
}
