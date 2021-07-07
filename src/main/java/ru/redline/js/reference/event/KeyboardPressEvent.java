package ru.redline.js.reference.event;

import lombok.Getter;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Getter
public class KeyboardPressEvent extends ScriptEvent<InputEvent.KeyInputEvent> {

    private int key;

    public KeyboardPressEvent(InputEvent.KeyInputEvent original) {
        super(original);
        key = Keyboard.getEventKey();
    }



}
