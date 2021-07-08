package ru.redline.core.hooks;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.redline.core.hook.asm.Hook;

public class BrandHook {


    @Hook
    @SideOnly(Side.CLIENT)
    public static String getClientModName(ClientBrandRetriever etot) {
        return "RedLine 1.12.2";
    }
}
