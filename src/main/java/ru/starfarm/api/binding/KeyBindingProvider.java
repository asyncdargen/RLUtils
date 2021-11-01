package ru.starfarm.api.binding;

import ru.starfarm.api.binding.bind.Category;

public interface KeyBindingProvider {

    Category register(Category categoryId);

    void unregister(String categoryId);

    boolean isRegistered(String categoryId);

}
