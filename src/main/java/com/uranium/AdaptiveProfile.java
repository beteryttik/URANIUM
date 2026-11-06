package com.uranium;

import net.minecraft.client.MinecraftClient;

public class AdaptiveProfile {
    public enum Profile {
        PERFORMANCE, BALANCED, QUALITY;

        public double particleDist() {
            return this == PERFORMANCE ? 8.0 : this == BALANCED ? 16.0 : 32.0;
        }
        public double entityCullDist() {
            return this == PERFORMANCE ? 32.0 : this == BALANCED ? 48.0 : 64.0;
        }
        public boolean hideShadows() { return this != QUALITY; }
        public boolean removeFog()   { return this != QUALITY; }
        public double renderScale()  {
            return this == PERFORMANCE ? 0.70 : this == BALANCED ? 0.85 : 1.00;
        }
    }

    private static Profile current = Profile.QUALITY;
    private static float fpsAvg = 60f;

    public static void init() {
        current = Profile.QUALITY;
        fpsAvg = 60f;
    }

    public static void tick() {
        if (!UraniumConfig.adaptive) { current = Profile.QUALITY; return; }
        MinecraftClient client = MinecraftClient.getInstance();
        float fps = client.getCurrentFps();
        if (fps <= 0) return;
        fpsAvg = fpsAvg * 0.95f + fps * 0.05f;

        Profile target;
        if (fpsAvg < 60) target = Profile.PERFORMANCE;
        else if (fpsAvg < 90) target = Profile.BALANCED;
        else target = Profile.QUALITY;

        if (target != current) {
            current = target;
            UraniumClient.LOGGER.info("[URANIUM] adaptive profile -> {}", current);
        }
    }

    public static Profile current() { return current; }
    public static float avgFps() { return fpsAvg; }
}
