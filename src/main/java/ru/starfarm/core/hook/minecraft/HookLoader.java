package ru.starfarm.core.hook.minecraft;

import net.minecraftforge.fml.common.asm.transformers.DeobfuscationTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import ru.starfarm.core.hook.asm.AsmHook;
import ru.starfarm.core.hook.asm.ClassMetadataReader;
import ru.starfarm.core.hook.asm.HookClassTransformer;

import java.util.Map;

/**
 * Удобная базовая реализация IFMLLoadingPlugin для использования HookLib.
 * Регистрировать хуки и контейнеры нужно в registerHooks().
 */
public abstract class HookLoader implements IFMLLoadingPlugin {

    private static DeobfuscationTransformer deobfuscationTransformer;

    private static ClassMetadataReader deobfuscationMetadataReader;

    static {
        deobfuscationMetadataReader = new DeobfuscationMetadataReader();
    }

    public static HookClassTransformer getTransformer() {
        return PrimaryClassTransformer.instance.registeredSecondTransformer ?
                MinecraftClassTransformer.instance : PrimaryClassTransformer.instance;
    }

    /**
     * Регистрирует вручную созданный хук
     */
    public static void registerHook(AsmHook hook) {
        getTransformer().registerHook(hook);
    }

    /**
     * Деобфусцирует класс с хуками и регистрирует хуки из него
     */
    public static void registerHookContainer(String className) {
        getTransformer().registerHookContainer(className);
    }

    public static ClassMetadataReader getDeobfuscationMetadataReader() {
        return deobfuscationMetadataReader;
    }
	
	static DeobfuscationTransformer getDeobfuscationTransformer() {
        if (HookLibPlugin.getObfuscated() && deobfuscationTransformer == null) {
            deobfuscationTransformer = new DeobfuscationTransformer();
        }
        return deobfuscationTransformer;
    }

    // 1.6.x only
    public String[] getLibraryRequestClass() {
        return null;
    }

    // 1.7.x only
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{getTransformer().getClass().getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        registerHooks();
    }

    protected abstract void registerHooks();
}
