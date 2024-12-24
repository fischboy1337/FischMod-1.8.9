package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;

import java.awt.*;

@ModInfo(name = "Hit Color", description = "Changes the color of damaged entities.", category = ModuleCategory.GAMEPLAY)
public class HitColorMod extends Module {

    public ColorValue color = new ColorValue("Damage Color", new Color(255, 0, 0));
}
