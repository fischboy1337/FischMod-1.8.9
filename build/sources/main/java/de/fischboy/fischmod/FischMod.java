package de.fischboy.fischmod;

import de.fischboy.fischmod.management.config.ConfigManager;
import de.fischboy.fischmod.management.event.EventManager;
import de.fischboy.fischmod.management.keybinding.KeybindingManager;
import de.fischboy.fischmod.management.module.ModuleManager;
import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

@Getter
@Mod(modid = FischMod.NAME, version = FischMod.VERSION)
public class FischMod {

    public static final FischMod INSTANCE = new FischMod();

    public static final String NAME = "FischMod";
    public static final String VERSION = "1.6-Dev";
    public static final String[] AUTHORS = {"fischboy"};

    private final EventManager eventManager = new EventManager();
    private final KeybindingManager keybindingManager = new KeybindingManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final ConfigManager configManager = new ConfigManager();

    public void start() {
        this.eventManager.register(this);
        this.keybindingManager.onStart();
        this.moduleManager.onStart();

        try {
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.configManager.getModConfig().save();
        this.eventManager.clear();
    }
}