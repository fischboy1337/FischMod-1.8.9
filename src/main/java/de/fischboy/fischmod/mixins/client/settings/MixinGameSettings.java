package de.fischboy.fischmod.mixins.client.settings;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.keybinding.ClientKeybinding;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public class MixinGameSettings {

    @Shadow
    public KeyBinding[] keyBindings;

    @Shadow
    public KeyBinding keyBindSpectatorOutlines;

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void gameSettingsConstructor(CallbackInfo callbackInfo) {
        this.keyBindings = ArrayUtils.addAll(keyBindings, FischMod.INSTANCE.getKeybindingManager().toArray(new ClientKeybinding[0]));
    }
}
