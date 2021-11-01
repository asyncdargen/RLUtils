package ru.starfarm.mod.boards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Board {

    private final double x, y, z, yaw;
    private final String title;
    private final String posTitle;
    private final String nameTitle;
    private final String valueTitle;
    private final List<Content> contents = new LinkedList<>();

    public void clear() {
        contents.clear();
    }

    public void update(List<Content> contents) {
        clear();
        add(contents);
    }

    public void add(List<Content> contents) {
        this.contents.addAll(contents);
    }

    public void add(Content... contents) {
        this.contents.addAll(Arrays.asList(contents));
    }



}
