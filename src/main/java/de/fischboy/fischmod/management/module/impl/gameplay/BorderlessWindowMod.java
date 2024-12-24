package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

@ModInfo(name = "Borderless Window", description = "Borderless", category = ModuleCategory.GAMEPLAY)
public class BorderlessWindowMod extends Module {

    private DisplayMode orginalDisplayMode;

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        this.setBorderlessWindow();
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        this.resetWindow();
    }

/*
    @EventTarget
    public void onTick(GameTickEvent event) {
        boolean fullScreenNow = mc.isFullScreen();
        if (this.lastFullscreen != fullScreenNow) {
            this.fix(fullScreenNow);
            this.lastFullscreen = fullScreenNow;
        }
    }
*/

/*
    public void fix(boolean fullscreen) {
        try {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setFullscreen(true);
                Display.setLocation(0, 0);
                Display.setResizable(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(mc.displayWidth, mc.displayHeight));
                Display.setLocation(0, 0);
                Display.setResizable(true);
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
*/

    private void setBorderlessWindow() {
        try {
            orginalDisplayMode = Display.getDisplayMode();

            DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            Display.setDisplayMode(desktopDisplayMode);
            Display.setResizable(false);
            Display.setFullscreen(false);
            Display.setLocation(0, 0);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private void resetWindow() {
        try {
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            if (orginalDisplayMode != null) {
                Display.setDisplayMode(orginalDisplayMode);
            }
            Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
}
