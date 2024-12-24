package de.fischboy.fischmod.management.module.impl.cheat;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.visual.RenderCheatUtil;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render3DEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

import java.awt.*;

@ModInfo(name = "ChestESP",  description = "ESP for Chest", category = ModuleCategory.CHEAT)
public class ChestESP extends Module {

    public ColorValue colorChest = new ColorValue("Chest Color", new Color(255,133,0));
    public ColorValue colorEnderChest = new ColorValue("EnderChest Color", new Color(163,0,255));
    public CheckBoxValue outline = new CheckBoxValue("Outline", true);
    public CheckBoxValue shade = new CheckBoxValue("Shade", false);

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest) {
                RenderCheatUtil.renderChest(tileEntity.getPos(), colorChest.getValue().getRGB(), outline.getValue(), shade.getValue());
            }else if (tileEntity instanceof TileEntityEnderChest) {
                RenderCheatUtil.renderChest(tileEntity.getPos(), colorEnderChest.getValue().getRGB(), outline.getValue(),shade.getValue());
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

