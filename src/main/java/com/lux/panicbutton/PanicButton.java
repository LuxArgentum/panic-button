package com.lux.panicbutton;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PanicButton implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("panic-button");

    @Override
    public void onInitialize() {
        LOGGER.info("Panic Button is running");
    }
}
