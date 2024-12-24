package de.fischboy.fischmod.management.module.impl.debug;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.*;

import java.awt.*;
import java.util.Arrays;

@Bounds(posX = 20, posY = 5, width = 100, height = 20)
@ModInfo(name = "TEST", description = "This is an Test only Mod", category = ModuleCategory.DEBUG)
public class TestMod extends HudModule {

    private CheckBoxValue check = new CheckBoxValue("Custom Text", false);
    private SliderValue slider = new SliderValue("Slider", 1, 10, 5, 1);
    private TextValue text = new TextValue("Text", "Type here...");
    private ColorValue color = new ColorValue("Color", new Color(0, 0, 0));
    private ModeValue mode = new ModeValue("Mode", Arrays.asList("Mode 1", "Mode 2", "Mode 3"), "Mode 2");

    @Override
    public void render() {
        RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
        float centerX = this.getPosX() + this.getBounds().width() / 2;
        float centerY = (this.getPosY() + this.getBounds().height() / 2) - (float) mc.fontRendererObj.FONT_HEIGHT / 2;
        if (!check.getValue()) {
            RenderUtil.drawCenteredString("Border: " + check.getValue() + " slider: " + slider.getValue(), centerX, centerY, Color.WHITE);
        } else {
            if (mode.getCurrentModes().equals("Mode 2")) {
                RenderUtil.drawCenteredString(text.getValue(), centerX, centerY, color.getValue());
            } else {
                RenderUtil.drawCenteredString(text.getValue(), centerX, centerY, Color.WHITE);
            }
        }
    }

    @Override
    public void renderDummy() {
        this.render();
    }
}
