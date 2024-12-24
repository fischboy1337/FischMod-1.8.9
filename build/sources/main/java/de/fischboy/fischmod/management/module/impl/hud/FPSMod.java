package de.fischboy.fischmod.management.module.impl.hud;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.TextValue;
import net.minecraft.client.Minecraft;

import java.awt.*;

@Bounds(posX = 20, posY = 5, width = 100, height = 20)
@ModInfo(name = "FPS", description = "Shows your FPS", category = ModuleCategory.HUD)
public class FPSMod extends HudModule {

    public TextValue customText = new TextValue("Custom Text", "FPS: %fps%");

    @Override
    public void render() {
        RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
        float centerX = this.getPosX() + this.getBounds().width() / 2;
        float centerY = (this.getPosY() + this.getBounds().height() / 2) - (float) mc.fontRendererObj.FONT_HEIGHT / 2;
        RenderUtil.drawCenteredString(formatText(customText.getValue()), centerX, centerY, Color.WHITE);
    }

    private String formatText(String text) {
        text = text.toLowerCase().replace("%fps%", String.valueOf(Minecraft.getDebugFPS()));
        return text;
    }

    @Override
    public void renderDummy() {
        this.render();
    }
}
