package ru.starfarm.js;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.starfarm.js.reference.EventReference;
import ru.starfarm.js.reference.event.ChatReceiveEvent;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.mod.notify.Notify;
import ru.starfarm.mod.session.PlayerController;
import ru.starfarm.mod.session.ServerInfo;
import ru.starfarm.util.*;
import ru.starfarm.util.format.TimeFormatter;
import ru.starfarm.util.io.NetUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

@Getter
public class JavaScriptManager {

    private final File FOLDER = new File(SFUtils.FOLDER, "scripts");

    private final JavaScriptBinder BINDER = new JavaScriptBinder();
    private final Map<String, JavaScript> scripts = new HashMap<>();

    private final NashornScriptEngineFactory engineManager = new NashornScriptEngineFactory();
    private final SFUtils mod;

    public JavaScriptManager(SFUtils mod) {
        this.mod = mod;
        configureBinder();
    }

    private void configureBinder() {
        BINDER.addBind("Render", mod.getRender());
        BINDER.addBind("WorldRender", mod.getRender().getWorldRender());
        BINDER.addBind("Player", mod.getPlayerProvider());
        BINDER.addBind("Network", mod.getNetwork());
        BINDER.addBind("Notifies", mod.getNotifies());
        BINDER.addBind("Discord", mod.getRpc());
        BINDER.addBind("JS", this);

        BINDER.addType("PlayerController", PlayerController.class);
        BINDER.addType("Notify", Notify.class);
        BINDER.addType("ServerInfo", ServerInfo.class);
        //Event enums
        BINDER.addType("Event", EventReference.Event.class);
        BINDER.addType("ChatType", ChatReceiveEvent.Type.class);
        //Util classes
        BINDER.addType("SysUtil", SystemUtils.class);
        BINDER.addType("TimeFormatter", TimeFormatter.class);
        BINDER.addType("NetUtil", NetUtil.class);
        BINDER.addType("Gson", GsonSerializer.class);
        BINDER.addType("Colors", Colors.class);
        BINDER.addType("ChatColor", ChatColor.class);
        BINDER.addType("Clipboard", ClipboardUtils.class);
        //Input
        BINDER.addType("Mouse", Mouse.class);
        BINDER.addType("Keyboard", Keyboard.class);
    }

    @SneakyThrows
    public void localScriptsWatcher() {
        val watch = FileSystems.getDefault().newWatchService();
        FOLDER.toPath().register(watch, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        new Thread(() -> {
            WatchKey key = null;

                while (true) {
                    try {
                        key = key = watch.take();
                    } catch (Throwable e) {
                        continue;
                    }
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == OVERFLOW) continue;
                        Path path = (Path) event.context();
                        String name = path.toFile().getName().toLowerCase();
                        if (!name.endsWith(".js")) break;
                        name = name.replace(".js", "");
                        val script = getScript(name);
                        if (event.kind() == ENTRY_DELETE && script != null) {
                            script.unload();
                            break;
                        }
                        try {
                            loadScript(name, loadCode(new File(FOLDER, path.toFile().getName()).toPath()));
                        } catch (Throwable e) {
                            mod.getLogger().error("Error while load script from wathc service, " + path, e);
                        }
                    }
                    key.reset();
                }
        }, "Scripts watch service").start();
    }

    public void localScripts() {
        if (!FOLDER.exists()) FOLDER.mkdirs();
        if (!FOLDER.isDirectory()) return;
        for (File scriptFile : FOLDER.listFiles((d, n) -> n.toLowerCase().endsWith(".js"))) {
            try {
                loadScript(scriptFile.getName().toLowerCase().replace(".js", ""), loadCode(scriptFile.toPath()));
            } catch (Throwable e) {
                mod.getLogger().error("Error while load script from folder, " + scriptFile, e);
            }
        }
    }

    @SneakyThrows
    private String loadCode(Path path) {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    public void loadScript(String name, String code) {
        val script = getScript(name);
        if (script != null) script.unload();
        createScript(name, code).load();
        mod.getLogger().info("Scripts - " + scripts);
    }

    public JavaScript createScript(String name, String code) {
        val engine = engineManager.getScriptEngine();
        val bindings = engine.createBindings();
        return new JavaScript(name, code, BINDER.putBinds(engine, bindings), bindings);
    }

    public void addScript(JavaScript script) {
        if (scripts.containsKey(script.getName()))
            removeScript(script);

        scripts.put(script.getName(), script);
    }

    public void removeScript(JavaScript script) {
        scripts.remove(script.getName());
    }

    public void removeScript(String name) {
        scripts.remove(name);
    }

    public JavaScript getScript(String name) {
        return scripts.get(name);
    }
}
