package com.uranium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UraniumClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("URANIUM");
    public static final String MODID = "uranium";

    @Override
    public void onInitializeClient() {
        UraniumConfig.load();
        AdaptiveProfile.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            UraniumOptimizer.tick();
            AdaptiveProfile.tick();
            HudCache.tick(client.player);
            BackgroundLimiter.tick(client);
        });

        LOGGER.info("[URANIUM] initialized");
    }
}
