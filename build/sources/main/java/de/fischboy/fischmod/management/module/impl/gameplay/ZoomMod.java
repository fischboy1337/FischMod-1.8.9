package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.animation.Animate;
import de.fischboy.fischmod.api.utils.animation.Easing;
import de.fischboy.fischmod.api.utils.visual.ScrollHelper;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render2DEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import de.fischboy.fischmod.management.module.value.impl.KeybindValue;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@ModInfo(name = "Zoom", description = "Allows you to zoom", category = ModuleCategory.GAMEPLAY)
public class ZoomMod extends Module {

    public SliderValue zoomAmount = new SliderValue("Zoom Amount", 30, 100, 30, 1);
    public KeybindValue keybind = new KeybindValue("Keybind", Keyboard.KEY_C);
    public CheckBoxValue smoothZoom = new CheckBoxValue("smoothZoom", true);

    private static final Animate animate = new Animate();
    private static final ScrollHelper scrollHelper = new ScrollHelper(0, 0, 5, 50);
    private static boolean zoom = false;

    public ZoomMod() {
        animate.setEase(Easing.LINEAR).setSpeed(700);
    }

    @Override
    public void onEnable() {
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        FischMod.INSTANCE.getEventManager().unregister(this);
    }

    @EventTarget
    public void onRender(Render2DEvent event) {
        animate.setMin(getAmount() / 2).setMax(mc.gameSettings.fovSetting).update();
        scrollHelper.setMinScroll(isSmooth() ? animate.getValueI() - 5 : getAmount() - 5);
        scrollHelper.update();

        if (zoom && mc.currentScreen == null) {
            scrollHelper.updateScroll();
        } else {
            scrollHelper.setScrollStep(0);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        zoom = Keyboard.isKeyDown(getKey());
        animate.setReversed(zoom);
    }

    public float getFOV() {
        if (isSmooth()) {
            return animate.getValueI() - scrollHelper.getCalculatedScroll();
        }
        return zoom ? getAmount() - scrollHelper.getCalculatedScroll() : mc.gameSettings.fovSetting;
    }

    public static boolean isZoom() {
        return zoom;
    }

    private boolean isSmooth() {
        return smoothZoom.getValue();
    }

    private int getKey() {
        return keybind.getValue();
    }

    private float getAmount() {
        return zoomAmount.getValue();
    }
}
