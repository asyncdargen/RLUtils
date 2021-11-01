package ru.starfarm.mod;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.starfarm.api.binding.ChatBinding;
import ru.starfarm.api.binding.ChatBindingProvider;
import ru.starfarm.api.binding.KeyBinding;
import ru.starfarm.api.binding.KeyBindingProvider;
import ru.starfarm.api.render.Render;
import ru.starfarm.api.render.RenderProvider;
import ru.starfarm.api.scheduler.Scheduler;
import ru.starfarm.api.scheduler.SchedulerProvider;
import ru.starfarm.api.status.StatusBarProvider;
import ru.starfarm.api.status.StatusBars;
import ru.starfarm.js.JavaScriptManager;
import ru.starfarm.mod.boards.BoardProvider;
import ru.starfarm.mod.boards.LeaderBoards;
import ru.starfarm.mod.discord.DiscordRPC;
import ru.starfarm.mod.gui.Customatic;
import ru.starfarm.mod.module.ModuleProvider;
import ru.starfarm.mod.module.Modules;
import ru.starfarm.mod.module.modules.AzerusModule;
import ru.starfarm.mod.module.modules.MainModule;
import ru.starfarm.mod.network.Network;
import ru.starfarm.mod.notify.NotificationProvider;
import ru.starfarm.mod.notify.Notifications;
import ru.starfarm.mod.session.PlayerProvider;
import ru.starfarm.util.Statistic;

import java.io.File;
import java.util.Arrays;

@Mod(modid = "sfutils", name = "SFUtils", version = "1.0")
@Getter
public class SFUtils {
    public static final String MOD_ID = "sfutils";
    public static final String MOD_NAME = "SFUtils";
    public static final String VERSION = "1.0";

    public static SFUtils INSTANCE;
    public static File FOLDER = new File(System.getProperty("user.dir"), "StarFarm");
    public static boolean developer;

    private final Logger logger;
    private final Minecraft minecraft;

    private final RenderProvider render;
    private final StatusBarProvider bars;
    private final SchedulerProvider scheduler;
    private final KeyBindingProvider keyBinding;
    private final ChatBindingProvider chatBinding;
    private final ModuleProvider modules;
    private final NotificationProvider notifies;
    private final BoardProvider boards;

    private final JavaScriptManager javaScriptManager;
    private final PlayerProvider playerProvider;
    private final DiscordRPC rpc;
    private final Network network;
    private final Customatic customatic;

    @SneakyThrows
    public SFUtils() {

        developer = Boolean.parseBoolean(System.getProperty("developer"));
        if (!FOLDER.exists()) FOLDER.mkdirs();

        INSTANCE = this;
        minecraft = Minecraft.getMinecraft();
        logger = LogManager.getLogger(SFUtils.class);

        render = new Render(this);
        bars = new StatusBars(this);
        scheduler = new Scheduler(this);
        keyBinding = new KeyBinding(this);
        chatBinding = new ChatBinding(this);
        notifies = new Notifications(this);
        playerProvider = new PlayerProvider(this);
        modules = new Modules(this);
        rpc = new DiscordRPC(this);
        network = new Network(this);
        customatic = new Customatic(this);
        boards = new LeaderBoards(this);
        javaScriptManager = new JavaScriptManager(this);

        if (developer) {
            logger.info("SFUtils working in developer mode | User: " + playerProvider.getName() + "!");
            javaScriptManager.localScripts();
            javaScriptManager.localScriptsWatcher();
        }
//        else Statistic.send(playerProvider.getName());

        modules.registerModule(MainModule.class);
        modules.registerModule(AzerusModule.class);

        System.out.println(Arrays.toString(GuiChat.class.getDeclaredMethods()));
        System.out.println(Arrays.toString(GuiScreen.class.getDeclaredMethods()));

    }

    public void registerEvents(Object... objects) {
        Arrays.stream(objects).forEach(MinecraftForge.EVENT_BUS::register);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        INSTANCE.customatic.startScheduler();
    }

}
