package com.uranium;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class UraniumConfig {
    public static boolean particleCull = true;
    public static double particleDist = 16.0;
    public static boolean entityCull = true;
    public static double entityCullDist = 64.0;
    public static boolean hideShadows = true;
    public static boolean removeFog = true;
    public static boolean hideVignette = false;
    public static boolean adaptive = true;
    public static boolean menuThrottle = true;
    public static int menuFps = 20;
    public static boolean backgroundLimit = true;
    public static int backgroundFps = 10;
    public static boolean hudCache = true;

    private static final Path PATH = net.fabricmc.loader.api.FabricLoader.getInstance()
            .getConfigDir().resolve("uranium.properties");

    public static void load() {
        if (!Files.exists(PATH)) { save(); return; }
        try (InputStream in = Files.newInputStream(PATH)) {
            Properties p = new Properties();
            p.load(in);
            particleCull = Boolean.parseBoolean(p.getProperty("particleCull", "true"));
            particleDist = Double.parseDouble(p.getProperty("particleDist", "16"));
            entityCull = Boolean.parseBoolean(p.getProperty("entityCull", "true"));
            entityCullDist = Double.parseDouble(p.getProperty("entityCullDist", "64"));
            hideShadows = Boolean.parseBoolean(p.getProperty("hideShadows", "true"));
            removeFog = Boolean.parseBoolean(p.getProperty("removeFog", "true"));
            hideVignette = Boolean.parseBoolean(p.getProperty("hideVignette", "false"));
            adaptive = Boolean.parseBoolean(p.getProperty("adaptive", "true"));
            menuThrottle = Boolean.parseBoolean(p.getProperty("menuThrottle", "true"));
            menuFps = Integer.parseInt(p.getProperty("menuFps", "20"));
            backgroundLimit = Boolean.parseBoolean(p.getProperty("backgroundLimit", "true"));
            backgroundFps = Integer.parseInt(p.getProperty("backgroundFps", "10"));
            hudCache = Boolean.parseBoolean(p.getProperty("hudCache", "true"));
        } catch (Exception e) {
            UraniumClient.LOGGER.warn("[URANIUM] config load failed", e);
        }
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("particleCull", String.valueOf(particleCull));
        p.setProperty("particleDist", String.valueOf(particleDist));
        p.setProperty("entityCull", String.valueOf(entityCull));
        p.setProperty("entityCullDist", String.valueOf(entityCullDist));
        p.setProperty("hideShadows", String.valueOf(hideShadows));
        p.setProperty("removeFog", String.valueOf(removeFog));
        p.setProperty("hideVignette", String.valueOf(hideVignette));
        p.setProperty("adaptive", String.valueOf(adaptive));
        p.setProperty("menuThrottle", String.valueOf(menuThrottle));
        p.setProperty("menuFps", String.valueOf(menuFps));
        p.setProperty("backgroundLimit", String.valueOf(backgroundLimit));
        p.setProperty("backgroundFps", String.valueOf(backgroundFps));
        p.setProperty("hudCache", String.valueOf(hudCache));
        try (OutputStream out = Files.newOutputStream(PATH)) {
            p.store(out, "URANIUM config");
        } catch (IOException e) {
            UraniumClient.LOGGER.warn("[URANIUM] config save failed", e);
        }
    }
}
