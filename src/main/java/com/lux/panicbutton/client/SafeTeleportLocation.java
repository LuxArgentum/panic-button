package com.lux.panicbutton.client;

import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.world.World;

import java.util.Set;

public record SafeTeleportLocation(
        World world,
        double xCoordinate,
        double yCoordinate,
        double zCoordinate,
        float yaw,
        float pitch
) {
}
