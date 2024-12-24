package de.fischboy.fischmod.mixins.client.renderer;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.event.impl.Render3DEvent;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE))
    public void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        new Render3DEvent(partialTicks).call();
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("NoHurtCam").isEnabled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "getFOVModifier", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;fovSetting:F"))
    public float modifyFOV(GameSettings gameSettings) {
        if (FischMod.INSTANCE.getModuleManager().getModByName("Zoom").isEnabled()) {
            return FischMod.INSTANCE.getModuleManager().getZoomMod().getFOV();
        }
        return gameSettings.fovSetting;
    }
}
