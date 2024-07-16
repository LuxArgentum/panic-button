package com.lux.panicbutton.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class PanicButtonClient implements ClientModInitializer {
    private final ClientPlayerEntity player;
    private SafeTeleportLocation safeTeleportLocation;

    public PanicButtonClient() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        player = minecraftClient.player;
    }

    @Override
    public void onInitializeClient() {
        registerPanicButton();
        new PanicButtonCommands(this).registerCommands();
    }

    private void registerPanicButton() {
        // Bound panic button to the 'X' key
        KeyBinding panicButton = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.panic-button.panic",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.panic-button.panic"));

        // Register what to do when panic button is pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (panicButton.wasPressed()) {
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
