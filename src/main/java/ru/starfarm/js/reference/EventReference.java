package ru.starfarm.js.reference;

import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ru.starfarm.js.reference.event.*;
import ru.starfarm.mod.SFUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventReference implements JavaScriptReference {

    private Map<Event, Set<Consumer<ScriptEvent<?>>>> handlers;

    public EventReference() {
        handlers = new ConcurrentHashMap<>();
    }

    public void load() {
        SFUtils.INSTANCE.registerEvents(this);
    }

    public void unload() {
        MinecraftForge.EVENT_BUS.unregister(this);
        handlers.clear();
    }

    public void post(net.minecraftforge.fml.common.eventhandler.Event event) {
        val type = Event.byEventClass(event.getClass());
        if (type == null) return;
        post(type, type.wrapOriginal(event));
    }

    public synchronized void post(Event type, ScriptEvent<?> event) {
        val handlers = this.handlers.get(type);
        if (handlers == null) return;
        handlers.forEach(h -> post(h, event));
    }

    public void post(Consumer<ScriptEvent<?>> handler, ScriptEvent<?> event) {
        try {
            handler.accept(event);
        } catch (Throwable e) {
            SFUtils.INSTANCE.getLogger().error("Error while post event " + event + " to handler : " + handler, e);
        }
    }


    public void reg(Event type, Consumer<ScriptEvent<?>> handler) {
        val handlers = this.handlers.getOrDefault(type, new ConcurrentSet<>());
        handlers.add(handler);
        this.handlers.put(type, handlers);
    }

    public String getName() {
        return "Events";
    }

    @Getter
    public static enum Event {

        //Render
        RENDER_OVERLAY(RenderGameOverlayEvent.Text.class, RenderOverlayEvent.class),
        RENDER_OVERLAY_ELEMENT(RenderGameOverlayEvent.class, ElementOverlayRender.class),
        WORLD_RENDER(RenderWorldLastEvent.class, RenderWorldEvent.class),
        //Chat
        CHAT_SEND(ClientChatEvent.class, ChatSendEvent.class),
        CHAT_RECEIVE(ClientChatReceivedEvent.class, ChatReceiveEvent.class),
        //Network
        CONNECT_FROM_CLIENT(FMLNetworkEvent.ClientConnectedToServerEvent.class, ConnectEvent.class),
        DISCONNECT_FROM_SERVER(FMLNetworkEvent.ClientDisconnectionFromServerEvent.class, DisconnectEvent.class),
        PLUGIN_MESSAGE(ru.starfarm.mod.events.PluginMessageEvent.class, PluginMessageEvent.class),
        //Input
        KEYBOARD_PRESS(InputEvent.KeyInputEvent.class, KeyboardPressEvent.class),
        MOUSE_PRESS(InputEvent.MouseInputEvent.class, MousePressEvent.class);

        private Class< ? extends net.minecraftforge.fml.common.eventhandler.Event> original;
        private MethodHandle constructor;

        @SneakyThrows
        Event(Class<? extends net.minecraftforge.fml.common.eventhandler.Event> original,
              Class< ? extends ScriptEvent<?>> event) {
            this.original = original;
            if (original == null) return;
            constructor = MethodHandles.lookup().findConstructor(event, MethodType.methodType(void.class, original));
        }

        @SneakyThrows
        public ScriptEvent<?> wrapOriginal(net.minecraftforge.fml.common.eventhandler.Event event) {
            return constructor == null ? null : (ScriptEvent<?>) getConstructor().invoke(event);
        }

        public static Event byEventClass(Class< ? extends net.minecraftforge.fml.common.eventhandler.Event> clazz) {
            for (Event event : values()) {
                if (event.getOriginal() != null && clazz.equals(event.original)) return event;
            }
            return null;
        }
    }

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Text e) { post(e); }

    @SubscribeEvent
    public void on(ClientChatEvent e) { post(e); }

    @SubscribeEvent
    public void on(ClientChatReceivedEvent e) { post(e); }

    @SubscribeEvent
    public void on(FMLNetworkEvent.ClientConnectedToServerEvent e) { post(e); }

    @SubscribeEvent
    public void on(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) { post(e); }

    @SubscribeEvent
    public void on(ru.starfarm.mod.events.PluginMessageEvent e) { post(e); }

    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent e) { post(e); }

    @SubscribeEvent
    public void on(InputEvent.MouseInputEvent e) { post(e); }

    @SubscribeEvent
    public void on(RenderWorldLastEvent e) { post(e); }

    @SubscribeEvent
    public void on(RenderGameOverlayEvent e) { post(e); }

}
