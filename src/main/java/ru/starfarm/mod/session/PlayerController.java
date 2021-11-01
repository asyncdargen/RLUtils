package ru.starfarm.mod.session;

import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
public class PlayerController {

    private final Minecraft mc;
    private final PlayerProvider pp;

    public double getX() {
        return mc.player.posX;
    }

    public double getY() {
        return mc.player.posY;
    }

    public double getZ() {
        return mc.player.posZ;
    }

    public double getYaw() {
        return mc.player.cameraYaw;
    }

    public BlockPos getBlockPos() {
        return mc.player.getPosition();
    }


}
