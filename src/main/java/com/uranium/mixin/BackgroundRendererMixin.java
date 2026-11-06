package com.uranium.mixin;

import com.uranium.UraniumOptimizer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void uranium$noFog(Camera camera, BackgroundRenderer.FogType type,
                                      float viewDistance, boolean thickFog, float tickDelta,
                                      CallbackInfo ci) {
        if (UraniumOptimizer.removeFog) {
            RenderSystem.setShaderFogStart(Float.NEGATIVE_INFINITY);
            RenderSystem.setShaderFogEnd(Float.POSITIVE_INFINITY);
        }
    }
}
