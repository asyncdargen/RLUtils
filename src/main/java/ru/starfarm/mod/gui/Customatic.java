package ru.starfarm.mod.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;
import ru.starfarm.mod.SFUtils;

import java.nio.ByteBuffer;
import java.util.function.Function;

import static ru.starfarm.util.io.BufferUtil.*;

public class Customatic {

    private SFUtils mod;

    public Customatic(SFUtils mod) {
        this.mod = mod;
        setIcon();
        setDisplayName("StarFarm | 1.12.2");
        mod.registerEvents(this);
    }

    public void setIcon() {
        Display.setIcon(new ByteBuffer[]{
                resourceImage("assets/sfutils/textures/gui/logo/logo16.png"),
                resourceImage("assets/sfutils/textures/gui/logo/logo32.png")
        });
    }

    public void setDisplayName(String name) {
        Display.setTitle(name);
    }

    public void startScheduler() {
        mod.getScheduler().runTaskTimer(20, this::run);
    }

    public void run() { }

    @SubscribeEvent
    public void on(GuiOpenEvent e) {
        if (e.getGui() == null) return;
        val gui = Gui.byEventClass(e.getGui().getClass());
        if (gui == null) return;
        val newGui = gui.replace(e.getGui());
        e.setGui(newGui);
    }

    @AllArgsConstructor
    @Getter
    enum Gui {

        MAIN(GuiMainMenu.class, g -> new ru.starfarm.mod.gui.GuiMainMenu()),
//        CHAT(GuiChat.class, g -> new ru.starfarm.mod.gui.GuiChat(ReflectUtil.getValue((GuiChat) g, "field_146409_v"))),
        MULTIPLAYER(GuiMultiplayer.class, ru.starfarm.mod.gui.GuiMultiplayer::new);

        private Class< ? extends GuiScreen> original;
        private Function<GuiScreen, GuiScreen> getter;

        public GuiScreen replace(GuiScreen original) {
            return getter.apply(original);
        }

        public static Gui byEventClass(Class< ? extends GuiScreen> clazz) {
            for (Gui gui : values()) {
                if (gui.getOriginal() != null && clazz != null && clazz.equals(gui.original)) return gui;
            }
            return null;
        }
    }

}
