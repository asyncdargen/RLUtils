package ru.redline.js.reference.event;

import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ElementOverlayRender extends ScriptEvent<RenderGameOverlayEvent> {

    public ElementOverlayRender(RenderGameOverlayEvent original) {
        super(original);
    }

    public ElementType getType() {
        return ElementType.of(getOriginal().getType());
    }

    public static enum ElementType
    {
        ALL,
        HELMET,
        PORTAL,
        CROSSHAIRS,
        BOSSHEALTH, // All boss bars
        BOSSINFO,    // Individual boss bar
        ARMOR,
        HEALTH,
        FOOD,
        AIR,
        HOTBAR,
        EXPERIENCE,
        TEXT,
        HEALTHMOUNT,
        JUMPBAR,
        CHAT,
        PLAYER_LIST,
        DEBUG,
        POTION_ICONS,
        SUBTITLES,
        FPS_GRAPH,
        VIGNETTE;

        public static ElementType of(RenderGameOverlayEvent.ElementType type) {
            if (type.ordinal() <= values().length)
                return values()[type.ordinal()];

            return null;
        }
    }

}
