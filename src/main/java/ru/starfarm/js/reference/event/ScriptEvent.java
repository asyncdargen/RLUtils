package ru.starfarm.js.reference.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.Event;

@AllArgsConstructor
@Getter
public class ScriptEvent<E extends Event> {

    private E original;

    public void setCanceled(boolean canceled) {
        if (original.isCancelable()) original.setCanceled(canceled);
    }

    public boolean isCancelled() {
        return original.isCancelable() && original.isCanceled();
    }

}
