package com.uranium.mixin;

import com.uranium.UraniumConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyReturnValue;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyReturnValue(method = "getFramerateLimit", at = @At("RETURN"))
    private int uranium$menuThrottle(int original) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        if (UraniumConfig.menuThrottle && client.currentScreen != null) {
            return Math.min(original, UraniumConfig.menuFps);
        }
        return original;
    }
}
