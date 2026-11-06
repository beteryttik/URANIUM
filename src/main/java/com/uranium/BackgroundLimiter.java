package com.uranium;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class BackgroundLimiter {
    public static boolean focused = true;
    private static long lastFrameMs = 0;

    public static void tick(MinecraftClient client) {
        long handle = client.getWindow().getHandle();
        focused = GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_FOCUSED) != 0;
    }

    /** true — текущий кадр нужно пропустить (троттлинг в фоне) */
    public static boolean shouldSkipFrame() {
        if (!UraniumConfig.backgroundLimit || focused) return false;
        long now = System.currentTimeMillis();
        long interval = Math.max(1L, 1000L / Math.max(1, UraniumConfig.backgroundFps));
        if (now - lastFrameMs < interval) return true;
        lastFrameMs = now;
        return false;
    }
}
