package de.fischboy.fischmod.management.module.impl.hud;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.UpdateEvent;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import net.minecraft.potion.Potion;

import java.awt.*;

@Bounds(posX = 20, posY = 20, width = 110, height = 16F)
@ModInfo(name = "ToggleSprint", description = "Toggles your Sprint", category = ModuleCategory.GAMEPLAY)
public class ToggleSprintMod extends HudModule {

    public CheckBoxValue disableWhileSprinting = new CheckBoxValue("Disable while Sprinting", false);

    private boolean sprintingToggled = false;
    private String text = "[Sprinting (Toggled)]";

    @Override
    public void onEnable() {
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        FischMod.INSTANCE.getEventManager().unregister(this);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        float f = 0.8F;

        if (mc.gameSettings.keyBindSprint.isPressed()) {
            if (mc.thePlayer.isSprinting() && !sprintingToggled) {
                sprintingToggled = true;
            } else if (!mc.thePlayer.isSprinting() && !disableWhileSprinting.getValue()) {
                sprintingToggled = !sprintingToggled;
            } else if (mc.thePlayer.isSprinting() && disableWhileSprinting.getValue()) {
                sprintingToggled = !sprintingToggled;
            }
        }

        boolean flags = !mc.thePlayer.movementInput.sneak &&
                (mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying) &&
                !mc.thePlayer.isPotionActive(Potion.blindness) &&
                mc.thePlayer.movementInput.moveForward >= f &&
                !mc.thePlayer.isSprinting() &&
                !mc.thePlayer.isUsingItem() &&
                !mc.thePlayer.isCollidedHorizontally &&
                !mc.gameSettings.keyBindAttack.isKeyDown() &&
                sprintingToggled;

        if (flags) {
            mc.thePlayer.setSprinting(true);
        }

        if (mc.thePlayer.isSprinting()) {
            text = ("[Sprinting " + (sprintingToggled ? "(Toggled)]" : "(Vanilla)]"));
        }
    }

    @Override
    public void render() {
        if (sprintingToggled || mc.thePlayer.isSprinting()) {
            RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
            float centerX = this.getPosX() + this.getBounds().width() / 2;
            float centerY = (this.getPosY() + this.getBounds().height() / 2) - (float) mc.fontRendererObj.FONT_HEIGHT / 2;
            RenderUtil.drawCenteredString(text, centerX, centerY, Color.WHITE);
        }
    }

    @Override
    public void renderDummy() {
        RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
        float centerX = this.getPosX() + this.getBounds().width() / 2;
        float centerY = (this.getPosY() + this.getBounds().height() / 2) - (float) mc.fontRendererObj.FONT_HEIGHT / 2;
        RenderUtil.drawCenteredString("[Sprinting (Toggled)]", centerX, centerY, Color.WHITE);
    }
}
