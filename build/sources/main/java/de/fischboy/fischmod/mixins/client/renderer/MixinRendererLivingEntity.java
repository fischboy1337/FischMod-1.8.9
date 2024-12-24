package de.fischboy.fischmod.mixins.client.renderer;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.module.impl.gameplay.HitColorMod;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import java.nio.FloatBuffer;

@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity {

    @Redirect(method = "setBrightness", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 0))
    public FloatBuffer setRed(FloatBuffer instance, float v) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("Hit Color").isEnabled()) {
            Color color = (Color) FischMod.INSTANCE.getModuleManager()
                    .getValueByClass("Damage Color", HitColorMod.class)
                    .getValue();
            instance.put(color.getRed() / 255f);
        } else {
            instance.put(1f);
        }
        return instance;
    }

    @Redirect(method = "setBrightness", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 1))
    public FloatBuffer setGreen(FloatBuffer instance, float v) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("Hit Color").isEnabled()) {
            Color color = (Color) FischMod.INSTANCE.getModuleManager()
                    .getValueByClass("Damage Color", HitColorMod.class)
                    .getValue();
            instance.put(color.getGreen() / 255f);
        } else {
            instance.put(0f);
        }
        return instance;
    }

    @Redirect(method = "setBrightness", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 2))
    public FloatBuffer setBlue(FloatBuffer instance, float v) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("Hit Color").isEnabled()) {
            Color color = (Color) FischMod.INSTANCE.getModuleManager()
                    .getValueByClass("Damage Color", HitColorMod.class)
                    .getValue();
            instance.put(color.getBlue() / 255f);
        } else {
            instance.put(0f);
        }
        return instance;
    }

    @Redirect(method = "setBrightness", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 3))
    public FloatBuffer setAlpha(FloatBuffer instance, float v) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("Hit Color").isEnabled()) {
            Color color = (Color) FischMod.INSTANCE.getModuleManager()
                    .getValueByClass("Damage Color", HitColorMod.class)
                    .getValue();
            instance.put(color.getAlpha() / 255f);
        } else {
            instance.put(0.3f);
        }
        return instance;
    }
}
