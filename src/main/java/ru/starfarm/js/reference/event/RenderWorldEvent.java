package ru.starfarm.js.reference.event;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class RenderWorldEvent extends ScriptEvent<RenderWorldLastEvent> {

    public RenderWorldEvent(RenderWorldLastEvent original) {
        super(original);
    }

    public double getPartialTick() {
        return getOriginal().getPartialTicks();
    }

    public RenderGlobal getContext() {
        return getOriginal().getContext();
    }

}
