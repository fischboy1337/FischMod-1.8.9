package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class CheckBox extends SubComponent<CheckBoxValue> {

    public CheckBox(CheckBoxValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        if (value.getValue()) {
            RenderRoundedUtil.drawRoundedRect(x, y, height, height, 4, new Color(80, 180, 255));
        } else {
            RenderRoundedUtil.drawRoundedRect(x, y, height, height, 4, new Color(200, 200, 200));
        }

        font.drawString(value.getName(), (int) (x + height + 10), (int) (y + height / 2 - font.FONT_HEIGHT / 2), -1);
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            value.setValue(!value.getValue());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }
}