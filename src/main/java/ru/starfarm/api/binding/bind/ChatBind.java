package ru.starfarm.api.binding.bind;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ChatBind {

    private int key;
    private String message;
    private boolean enabled;
    private boolean suggest;

}
