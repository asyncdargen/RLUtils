package ru.redline.js.reference;

import lombok.SneakyThrows;
import lombok.val;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public interface JavaScriptReference {

    static Class<? extends JavaScriptReference>[] REFERENCES = new Class[] { SchedulerReference.class, EventReference.class };

    void load();

    void unload();

    String getName();

    @SneakyThrows
    static JavaScriptReference[] newReferences() {
        val references = new JavaScriptReference[REFERENCES.length];
        for (int i = 0; i < REFERENCES.length; i++) {
            references[i] = (JavaScriptReference) MethodHandles.lookup()
                    .findConstructor(REFERENCES[i], MethodType.methodType(void.class)).invoke();
        }
        return references;
    }
}
