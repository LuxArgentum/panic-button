package com.lux.panicbutton.client;

import com.lux.panicbutton.client.command.PanicButtonCommands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
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
        double destinationX = safeTeleportLocation.xCoordinate();
        double destinationY = safeTeleportLocation.yCoordinate();
        double destinationZ = safeTeleportLocation.zCoordinate();

        teleportPlayer(destinationX, destinationY, destinationZ);
    }

    private void teleportPlayer(double destinationX, double destinationY, double destinationZ) {
        player.teleport(destinationX, destinationY, destinationZ, false);
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
