package com.uranium.mixin;

import net.minecraft.client.MinecraftClient;

/** маленький хелпер для доступа к клиенту из миксинов */
public final class UraniumClientAccessor {
    private UraniumClientAccessor() {}

    public static MinecraftClient client() {
        return MinecraftClient.getInstance();
    }
}
