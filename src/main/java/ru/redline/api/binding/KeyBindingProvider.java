package ru.redline.api.binding;

import ru.redline.api.binding.bind.Category;

public interface KeyBindingProvider {

    Category register(Category categoryId);

    void unregister(String categoryId);

    boolean isRegistered(String categoryId);

}
