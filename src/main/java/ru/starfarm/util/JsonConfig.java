package ru.starfarm.util;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.starfarm.mod.SFUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;

@Getter
public class JsonConfig<T> {

    public static final File PATH = new File(SFUtils.FOLDER, "cfg");

    static {
        if (!PATH.isDirectory()) PATH.delete();
        if (!PATH.exists()) PATH.mkdirs();
    }

    private final File file;
    private final Type valueType;
    private T value;

    @SneakyThrows
    public JsonConfig(String name, T def) {
        valueType = new TypeToken<T>(getClass()){}.getType();
        file = new File(PATH, name + ".json");
        if (!file.exists()) {
            file.createNewFile();
            if (def != null) writeJson(def);
        }
    }

    public static <T> JsonConfig<T> of(String name, T def) {
        return new JsonConfig<T>(name, def);
    }

    @SneakyThrows
    public void writeJson(T value) {
        this.value = value;
        Files.write(file.toPath(), GsonSerializer.GSON.toJsonTree(value, valueType).toString().getBytes());
    }

    @SneakyThrows
    public T readJson() {
        return value = GsonSerializer.GSON.fromJson(new FileReader(file), valueType);
    }

    public void delete() {
        file.delete();
    }


}
