package com.lux.panicbutton.client.command;

import com.lux.panicbutton.client.PanicButtonClient;
import com.lux.panicbutton.client.SafeTeleportLocation;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PanicButtonCommands {
    private final PanicButtonClient panicButtonClient;

    public PanicButtonCommands(PanicButtonClient panicButtonClient) {
        this.panicButtonClient = panicButtonClient;
    }

    public void registerCommands() {
        registerSetSafeTeleportLocationCommand();
    }

    private void registerSetSafeTeleportLocationCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("setSafeTeleportLocation").executes(context -> {

                PlayerEntity player = panicButtonClient.getPlayer();

                World playerWorld = player.getWorld();
                double playerXCoordinate = player.getX();
                double playerYCoordinate = player.getY();
                double playerZCoordinate = player.getZ();
                float playerYaw = player.getYaw();
                float playerPitch = player.getPitch();

                SafeTeleportLocation newSafeTeleportLocation = new SafeTeleportLocation(playerWorld,
                                                                                        playerXCoordinate,
                                                                                        playerYCoordinate,
                                                                                        playerZCoordinate,
                                                                                        playerYaw,
                                                                                        playerPitch
                );
                panicButtonClient.setSafeTeleportLocation(newSafeTeleportLocation);

                return 1; // Success
            }));
        });
    }
}
