package de.fischboy.fischmod.management.module;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.containers.Storage;
import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import de.fischboy.fischmod.gui.HudConfigScreen;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render2DEvent;
import de.fischboy.fischmod.management.module.impl.cheat.BedESP;
import de.fischboy.fischmod.management.module.impl.cheat.ChestESP;
import de.fischboy.fischmod.management.module.impl.cheat.ESPMod;
import de.fischboy.fischmod.management.module.impl.cheat.MurderMysteryMod;
import de.fischboy.fischmod.management.module.impl.cosmetic.ChinaHatCosmetic;
import de.fischboy.fischmod.management.module.impl.debug.TestMod;
import de.fischboy.fischmod.management.module.impl.gameplay.*;
import de.fischboy.fischmod.management.module.impl.hud.CPSMod;
import de.fischboy.fischmod.management.module.impl.hud.FPSMod;
import de.fischboy.fischmod.management.module.impl.hud.KeystrokesMod;
import de.fischboy.fischmod.management.module.impl.hud.ToggleSprintMod;
import de.fischboy.fischmod.management.module.value.Value;
import lombok.Getter;
import lombok.Setter;

@Getter
@SuppressWarnings("unchecked")
public class ModuleManager extends Storage<Module> implements IMinecraft {

    @Setter
    private Module recentlyAddedMod;

    @Getter
    private TNTTimerMod tntTimerMod;
    private ZoomMod zoomMod;

    @Override
    public void onStart() {
        tntTimerMod = new TNTTimerMod();
        zoomMod = new ZoomMod();

        this.addAll(
                FPSMod::new,
                TestMod::new,
                ToggleSprintMod::new,
                ItemPhysicsMod::new,
                AnimationMod::new,
                FullbrightMod::new,
                KeystrokesMod::new,
                CPSMod::new,
                AutoGGMod::new,
                //BorderlessWindowMod::new,
                NoHurtCamMod::new,
                HitColorMod::new,
                BedESP::new,
                ESPMod::new,
                ChestESP::new,
                MurderMysteryMod::new,
                ChinaHatCosmetic::new,
                () -> zoomMod,
                () -> tntTimerMod
        );
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (!(mc.currentScreen instanceof HudConfigScreen)) {
            this.stream().filter(m -> m.isHud() && m.isEnabled()).forEach(m -> ((HudModule) m).render());
        }
    }

    public <T extends Module> T getModByName(String name) {
        return (T) findFirst(module -> module.getInfo().name().equalsIgnoreCase(name));
    }

    public <T extends Value<?>> T getValueByMod(String name, Module module) {
        return (T) module.getValues().stream()
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public <T extends Value<?>> T getValueByClass(String name, Class<? extends Module> module) {
        return (T) getByClass(module).getValues().stream()
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
