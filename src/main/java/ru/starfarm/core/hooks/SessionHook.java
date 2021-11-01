package ru.starfarm.core.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.starfarm.core.hook.asm.Hook;
import ru.starfarm.core.hook.asm.ReturnCondition;
import ru.starfarm.mod.SFUtils;


public class SessionHook {

    @SideOnly(Side.CLIENT)
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static Session getSession(Minecraft mc) {
        return SFUtils.INSTANCE.getPlayerProvider().getAltManager().getSession();
    }

}
