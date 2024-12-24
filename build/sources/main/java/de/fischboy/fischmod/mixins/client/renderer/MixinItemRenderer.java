package de.fischboy.fischmod.mixins.client.renderer;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.module.impl.gameplay.AnimationMod;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private ItemStack itemToRender;

    private float swingProgressSave = 0;

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getSwingProgress(F)F"))
    private float swingProgress(AbstractClientPlayer entity, float partialTicks) {
        float swing = entity.getSwingProgress(partialTicks);
        if (FischMod.INSTANCE.getModuleManager().getByClass(AnimationMod.class).isEnabled()) {
            swingProgressSave = swing;
        }
        return swing;
    }

    @ModifyArg(method = "transformFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0), index = 2)
    private float setRodAnimation(float z) {
        if (FischMod.INSTANCE.getModuleManager().getByClass(AnimationMod.class).isEnabled()) {
            CheckBoxValue rodsValue = FischMod.INSTANCE.getModuleManager().getValueByClass("Rods", AnimationMod.class);
            if (rodsValue.getValue()) {
                if (itemToRender.getItem() instanceof ItemFishingRod) {
                    return -0.98F;
                }
            }
        }
        return z;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 1)
    private float setAnimationSword(float swingProgress, float partialTicks) {
        if (FischMod.INSTANCE.getModuleManager().getByClass(AnimationMod.class).isEnabled()) {
            CheckBoxValue swordValue = FischMod.INSTANCE.getModuleManager().getValueByClass("Sword", AnimationMod.class);
            if (swordValue.getValue()) {
                return swingProgressSave;
            }
        }
        return swingProgress;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 3), index = 1)
    private float setAnimationBow(float swingProgress, float partialTicks) {
        if (FischMod.INSTANCE.getModuleManager().getByClass(AnimationMod.class).isEnabled()) {
            CheckBoxValue bowValue = FischMod.INSTANCE.getModuleManager().getValueByClass("Bow", AnimationMod.class);
            if (bowValue.getValue()) {
                return swingProgressSave;
            }
        }
        return swingProgress;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1), index = 1)
    private float setAnimationDrink(float swingProgress, float partialTicks) {
        if (FischMod.INSTANCE.getModuleManager().getByClass(AnimationMod.class).isEnabled()) {
            CheckBoxValue drinkValue = FischMod.INSTANCE.getModuleManager().getValueByClass("Drink", AnimationMod.class);
            if (drinkValue.getValue()) {
                return swingProgressSave;
            }
        }
        return swingProgress;
    }
}
