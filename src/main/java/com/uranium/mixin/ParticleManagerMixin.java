package com.uranium.mixin;

import com.uranium.UraniumClient;
import com.uranium.UraniumOptimizer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"), cancellable = true)
    private void uranium$cullParticles(ParticleEffect params, double x, double y, double z,
                                       double vx, double vy, double vz,
                                       CallbackInfoReturnable<Particle> cir) {
        if (!UraniumConfig.particleCull) return;

        if (UraniumOptimizer.particleDist <= 0 && params.getType() == ParticleTypes.EXPLOSION) {
            return; // взрывы всегда рисуем
        }

        if (params.getType() == ParticleTypes.EXPLOSION) return;

        var client = UraniumClientAccessor.client();
        ClientWorld world = client.world;
        if (world == null) return;
        Camera cam = client.gameRenderer.getCamera();
        double dx = x - cam.getPos().x;
        double dy = y - cam.getPos().y;
        double dz = z - cam.getPos().z;
        if (dx * dx + dy * dy + dz * dz > UraniumOptimizer.particleDist * UraniumOptimizer.particleDist) {
            cir.setReturnValue(null);
        }
    }
}
