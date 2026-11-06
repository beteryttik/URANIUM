package com.uranium.mixin;

import com.uranium.HudCache;
import com.uranium.UraniumConfig;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderVignette", at = @At("HEAD"), cancellable = true)
    private void uranium$noVignette(MatrixStack matrices, Entity entity, CallbackInfo ci) {
        if (UraniumConfig.hideVignette) ci.cancel();
    }

    // каркас кэша HUD: пока dirty — рисуем как обычно и сбрасываем флаг
    @Inject(method = "render", at = @At("HEAD"))
    private void uranium$hudCacheMark(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (HudCache.isDirty()) {
            // здесь в 0.2 будет blit из FBO, пока — обычный рендер
            HudCache.markDrawn();
        }
    }
}
