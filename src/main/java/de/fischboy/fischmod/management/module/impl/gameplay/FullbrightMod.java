package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.GameTickEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;

@ModInfo(name = "Fullbright", description = "Fullbright halt", category = ModuleCategory.GAMEPLAY)
public class FullbrightMod extends Module {

    public SliderValue brightness = new SliderValue("Brightness", 10, 100, 100, 1);

    private float oldGamma;

    @EventTarget
    public void onTick(GameTickEvent event) {
        mc.gameSettings.gammaSetting = brightness.getValue();
    }

    @Override
    public void onEnable() {
        if (mc.gameSettings != null) {
            oldGamma = mc.gameSettings.gammaSetting;
        }
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        if (mc.gameSettings != null) {
            mc.gameSettings.gammaSetting = oldGamma;
        }
        FischMod.INSTANCE.getEventManager().unregister(this);
    }
}
