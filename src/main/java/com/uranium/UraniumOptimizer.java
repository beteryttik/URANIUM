package com.uranium;

public class UraniumOptimizer {
    // значения, читаемые миксинами; перезаписываются адаптивным профилем
    public static double particleDist = UraniumConfig.particleDist;
    public static double entityCullDist = UraniumConfig.entityCullDist;
    public static boolean hideShadows = UraniumConfig.hideShadows;
    public static boolean removeFog = UraniumConfig.removeFog;

    public static void tick() {
        if (UraniumConfig.adaptive) {
            AdaptiveProfile.Profile p = AdaptiveProfile.current();
            particleDist = Math.min(UraniumConfig.particleDist, p.particleDist());
            entityCullDist = Math.min(UraniumConfig.entityCullDist, p.entityCullDist());
            hideShadows = UraniumConfig.hideShadows || p.hideShadows();
            removeFog = UraniumConfig.removeFog || p.removeFog();
        } else {
            particleDist = UraniumConfig.particleDist;
            entityCullDist = UraniumConfig.entityCullDist;
            hideShadows = UraniumConfig.hideShadows;
            removeFog = UraniumConfig.removeFog;
        }
    }
}
