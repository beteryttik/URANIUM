package com.uranium;

import net.minecraft.client.network.ClientPlayerEntity;

public class HudCache {
    private static int lastHealth = -1;
    private static int lastFood = -1;
    private static int lastAir = -1;
    public static boolean dirty = true;

    public static void tick(ClientPlayerEntity player) {
        if (player == null) return;
        int health = (int) Math.ceil(player.getHealth());
        int food = player.getHungerManager().getFoodLevel();
        int air = player.getAir();
        if (health != lastHealth || food != lastFood || air != lastAir) {
            dirty = true;
            lastHealth = health;
            lastFood = food;
            lastAir = air;
        }
    }

    public static void markDrawn() { dirty = false; }
    public static boolean isDirty() { return dirty; }
    public static boolean enabled() { return UraniumConfig.hudCache; }
}
