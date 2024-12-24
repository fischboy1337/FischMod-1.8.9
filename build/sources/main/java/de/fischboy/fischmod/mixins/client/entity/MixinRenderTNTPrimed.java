package de.fischboy.fischmod.mixins.client.entity;

import de.fischboy.fischmod.FischMod;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderTNTPrimed.class)
public class MixinRenderTNTPrimed {

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V", at = @At("HEAD"))
    public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        FischMod.INSTANCE.getModuleManager().getTntTimerMod().renderTag((RenderTNTPrimed) (Object) this, entity, x, y, z, partialTicks);
    }
}
