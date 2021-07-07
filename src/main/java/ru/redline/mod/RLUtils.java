package ru.redline.mod;

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
import ru.redline.api.binding.ChatBinding;
import ru.redline.api.binding.ChatBindingProvider;
import ru.redline.api.binding.KeyBinding;
import ru.redline.api.binding.KeyBindingProvider;
import ru.redline.api.render.Render;
import ru.redline.api.render.RenderProvider;
import ru.redline.api.scheduler.Scheduler;
import ru.redline.api.scheduler.SchedulerProvider;
import ru.redline.api.status.StatusBarProvider;
import ru.redline.api.status.StatusBars;
import ru.redline.js.JavaScriptManager;
import ru.redline.mod.boards.BoardProvider;
import ru.redline.mod.boards.LeaderBoards;
import ru.redline.mod.discord.DiscordRPC;
import ru.redline.mod.gui.Customatic;
import ru.redline.mod.module.ModuleProvider;
import ru.redline.mod.module.Modules;
import ru.redline.mod.module.modules.AzerusModule;
import ru.redline.mod.module.modules.MainModule;
import ru.redline.mod.network.Network;
import ru.redline.mod.notify.NotificationProvider;
import ru.redline.mod.notify.Notifications;
import ru.redline.mod.session.PlayerProvider;
import ru.redline.util.Statistic;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

@Mod(modid = "rlutils", name = "RLUtils", version = "1.0")
@Getter
public class RLUtils {
    public static final String MOD_ID = "rlutils";
    public static final String MOD_NAME = "RLUtils";
    public static final String VERSION = "1.0";

    public static RLUtils INSTANCE;
    public static File FOLDER = new File(System.getProperty("user.dir"), "RedLine");
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
    public RLUtils() {

        developer = Boolean.parseBoolean(System.getProperty("developer"));
        if (!FOLDER.exists()) FOLDER.mkdirs();

        INSTANCE = this;
        minecraft = Minecraft.getMinecraft();
        logger = LogManager.getLogger(RLUtils.class);

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
            logger.info("RLUtils working in developer mode | User: " + playerProvider.getName() + "!");
            javaScriptManager.localScripts();
            javaScriptManager.localScriptsWatcher();
        } else Statistic.send(playerProvider.getName());

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
