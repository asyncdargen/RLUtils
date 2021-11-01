package ru.starfarm.api.binding;

import ru.starfarm.api.binding.bind.ChatBind;

import java.util.List;

public interface ChatBindingProvider {

    void remove(ChatBind bind);

    ChatBind register(String message, int key, boolean suggest);

    List<ChatBind> getBinds();

    void update();

}
