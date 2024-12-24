package de.fischboy.fischmod.mixins.client.entity;

import de.fischboy.fischmod.management.event.impl.UpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    private final UpdateEvent event = new UpdateEvent();

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void onUpdate(CallbackInfo info) {
        event.call();
    }
}
