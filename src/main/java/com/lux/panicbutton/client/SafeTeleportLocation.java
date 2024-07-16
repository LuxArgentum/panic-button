package com.lux.panicbutton.client;

import net.minecraft.world.World;

public record SafeTeleportLocation(
        World world,
        double xCoordinate,
        double yCoordinate,
        double zCoordinate,
        float yaw,
        float pitch
) {
}
