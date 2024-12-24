package de.fischboy.fischmod.management.module.impl.cheat;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.visual.RenderCheatUtil;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render3DEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@ModInfo(name = "ESP", description = "ESP for Entities", category = ModuleCategory.CHEAT)
public class ESPMod extends Module {

    public ColorValue c = new ColorValue("Color", new Color(255,0,0));

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityPlayer) {
                if (e != mc.thePlayer && e != null) {
                    RenderCheatUtil.renderEntity(e, 1, 0, 0, c.getValue().getRGB(), false);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        FischMod.INSTANCE.getEventManager().unregister(this);
    }
}
