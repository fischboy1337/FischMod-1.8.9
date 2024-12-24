package de.fischboy.fischmod.management.keybinding.impl;

import de.fischboy.fischmod.gui.HudConfigScreen;
import de.fischboy.fischmod.management.keybinding.ClientKeybinding;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ClientMenuKeybind extends ClientKeybinding {

    private HudConfigScreen hudConfigScreen;

    public ClientMenuKeybind() {
        super("Client Menu", Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onKeyPressed() {
        if (Minecraft.getMinecraft().currentScreen == null) {
            if (hudConfigScreen == null) {
                hudConfigScreen = new HudConfigScreen();
            }
            Minecraft.getMinecraft().displayGuiScreen(hudConfigScreen);
        }
    }
}
