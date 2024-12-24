package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;

@ModInfo(name = "Animation", description = "Old Animation", category = ModuleCategory.GAMEPLAY)
public class AnimationMod extends Module {

    public CheckBoxValue rod = new CheckBoxValue("Rods", true);
    public CheckBoxValue sword = new CheckBoxValue("Sword", true);
    public CheckBoxValue bow = new CheckBoxValue("Bow", true);
    public CheckBoxValue drink = new CheckBoxValue("Drink", true);
}
