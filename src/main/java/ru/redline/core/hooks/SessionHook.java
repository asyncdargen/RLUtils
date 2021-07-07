package ru.redline.core.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.redline.core.hook.asm.Hook;
import ru.redline.core.hook.asm.ReturnCondition;
import ru.redline.mod.RLUtils;


public class SessionHook {

    @SideOnly(Side.CLIENT)
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static Session getSession(Minecraft mc) {
        return RLUtils.INSTANCE.getPlayerProvider().getAltManager().getSession();
    }

}
