package de.fischboy.fischmod.management.keybinding;

import de.fischboy.fischmod.FischMod;
import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;

@Getter
public abstract class ClientKeybinding extends KeyBinding {


    public ClientKeybinding(String description, int keyCode) {
        super(description, keyCode, FischMod.NAME);
    }

    public abstract void onKeyPressed();
}
