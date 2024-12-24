package de.fischboy.fischmod.mixins.client.gui;

import de.fischboy.fischmod.management.event.impl.Render2DEvent;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderTooltip", at = @At("RETURN"))
    public void renderTooltip(CallbackInfo info) {
        new Render2DEvent().call();
    }


}