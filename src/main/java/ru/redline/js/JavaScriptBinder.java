package ru.redline.js;

import lombok.SneakyThrows;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import java.util.HashMap;
import java.util.Map;

public class JavaScriptBinder {

    private Map<String, Object> binds = new HashMap<>();

    public void addBind(String name, Object param) {
        binds.put(name, param);
    }

    public void addType(String name, Class type) {
        binds.put(name, "Java.type(\"" + type.getName() + "\")");
    }

    public void addType(String name, String type) {
        binds.put(name, "Java.type(\"" + type + "\")");
    }


    public void addFunction(String name, String func) {
        binds.put(name, func);
    }

    public ScriptEngine putBinds(ScriptEngine engine, Bindings bindings) {
        binds.forEach((n, v) -> {
            String value = String.valueOf(v);
            if (value.startsWith("Java.type")) bindings.put(n, eval(engine, bindings, value));
            else if (value.startsWith("function")) eval(engine, bindings, value);
            else bindings.put(n, v);
        });
        return engine;
    }

    @SneakyThrows
    private Object eval(ScriptEngine engine, Bindings bindings, String script) {
        return engine.eval(script, bindings);
    }

}
