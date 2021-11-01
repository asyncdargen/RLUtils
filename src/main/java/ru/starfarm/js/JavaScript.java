package ru.starfarm.js;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.starfarm.js.reference.JavaScriptReference;
import ru.starfarm.mod.SFUtils;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public class JavaScript {

    private final JavaScriptReference[] references = JavaScriptReference.newReferences();
    private final String name;
    private final String code;
    private ScriptEngine engine;

    private Bindings bindings;

    public void load() {

        SFUtils.INSTANCE.getLogger().info("Load script " + name);
        try {
            Arrays.stream(references).forEach(r -> bindings.put(r.getName(), r));
            Arrays.stream(references).forEach(JavaScriptReference::load);
            engine.eval(code, bindings);
            SFUtils.INSTANCE.getJavaScriptManager().addScript(this);
        } catch (Throwable e) {
            SFUtils.INSTANCE.getLogger().error("Error while loading script " + name, e);
            unload();
        }
    }

    public void unload() {
        SFUtils.INSTANCE.getLogger().warn("Unload script " + name);
        try {
            Arrays.stream(references).forEach(JavaScriptReference::unload);
            bindings.clear();
            SFUtils.INSTANCE.getJavaScriptManager().removeScript(this);
        } catch (Throwable e) {
            SFUtils.INSTANCE.getLogger().error("Error while unloading script " + name, e);
        }
    }

}
