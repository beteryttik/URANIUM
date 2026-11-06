package com.uranium.mixin;

import com.uranium.UraniumOptimizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD"), cancellable = true)
    private void uranium$cullFarEntities(LivingEntity entity, float yaw, float delta,
                                         MatrixStack matrices, VertexConsumerProvider vertices, int light,
                                         CallbackInfo ci) {
        if (!UraniumConfig.entityCull || UraniumOptimizer.entityCullDist <= 0) return;
        if (entity == MinecraftClient.getInstance().player) return;

        var cam = MinecraftClient.getInstance().gameRenderer.getCamera();
        double dx = entity.getX() - cam.getPos().x;
        double dy = entity.getY() - cam.getPos().y;
        double dz = entity.getZ() - cam.getPos().z;
        if (dx * dx + dy * dy + dz * dz > UraniumOptimizer.entityCullDist * UraniumOptimizer.entityCullDist) {
            ci.cancel();
        }
    }
}
