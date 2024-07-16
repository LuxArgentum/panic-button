package com.lux.panicbutton.client;

import com.lux.panicbutton.client.command.PanicButtonCommands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class PanicButtonClient implements ClientModInitializer {
    // Bound panic teleport to the 'X' key
    KeyBinding panicTeleportButton = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.panic-button.teleport",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "category.panic-button.panic"
    ));
    private ClientPlayerEntity player;
    private SafeTeleportLocation safeTeleportLocation;

    @Override
    public void onInitializeClient() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        player = minecraftClient.player;

        registerPanicTeleportEventHandler();
        new PanicButtonCommands(this).registerCommands();
    }

    private void registerPanicTeleportEventHandler() {
        // Register what to do when panic teleport button is pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (panicTeleportButton.wasPressed()) {
                panicButtonPressed();
            }
        });
    }

    private void panicButtonPressed() {
        teleportPlayer(safeTeleportLocation);
    }

    private void teleportPlayer(SafeTeleportLocation safeTeleportLocation) {

        TeleportTarget teleportTarget = getTeleportTarget(safeTeleportLocation);
        player.teleportTo(teleportTarget);
    }

    private static @NotNull TeleportTarget getTeleportTarget(SafeTeleportLocation safeTeleportLocation) {
        ServerWorld destinationWorld = (ServerWorld) safeTeleportLocation.world();
        double xCoordinate = safeTeleportLocation.xCoordinate();
        double yCoordinate = safeTeleportLocation.yCoordinate();
        double zCoordinate = safeTeleportLocation.zCoordinate();
        float yaw = safeTeleportLocation.yaw();
        float pitch = safeTeleportLocation.pitch();

        Vec3d destinationPosition = new Vec3d(xCoordinate, yCoordinate, zCoordinate);
        Vec3d velocity = new Vec3d(0, 0, 0);

        return new TeleportTarget(destinationWorld, destinationPosition, velocity, yaw, pitch, TeleportTarget.NO_OP);
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public SafeTeleportLocation getSafeTeleportLocation() {
        return safeTeleportLocation;
    }

    public void setSafeTeleportLocation(SafeTeleportLocation safeTeleportLocation) {
        this.safeTeleportLocation = safeTeleportLocation;
    }
}
