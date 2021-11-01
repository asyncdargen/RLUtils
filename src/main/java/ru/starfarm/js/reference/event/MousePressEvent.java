package ru.starfarm.js.reference.event;

import lombok.Getter;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

@Getter
public class MousePressEvent extends ScriptEvent<InputEvent.MouseInputEvent> {

    private int button;

    public MousePressEvent(InputEvent.MouseInputEvent original) {
        super(original);
        button = Mouse.getEventButton();
    }
}
