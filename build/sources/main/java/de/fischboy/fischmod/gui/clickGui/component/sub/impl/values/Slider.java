package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class Slider extends SubComponent<SliderValue> {

    private boolean dragging = false;

    public Slider(SliderValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(50, 50, 50, 200));

        float sliderPosition = (value.getValue() - value.getMin()) / (value.getMax() - value.getMin()) * width;
        RenderRoundedUtil.drawRoundedRect(x, y, sliderPosition, height, 6, new Color(63, 150, 250));

        RenderRoundedUtil.drawRoundedRect(x + sliderPosition - 2, y, 4, height, 6, new Color(255, 255, 255));

        font.drawString(value.getName() + ": " + String.format("%.2f", value.getValue()),
                (int) (x + 10), (int) (y + height / 2 - font.FONT_HEIGHT / 2), Color.WHITE.getRGB());

        if (dragging) {
            updateSlider(mouseX);
        }
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
            updateSlider(mouseX);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (dragging) {
            updateSlider(mouseX);
        }
    }

    private void updateSlider(int mouseX) {
        float percent = Math.min(Math.max((mouseX - x) / width, 0), 1);
        float newValue = value.getMin() + percent * (value.getMax() - value.getMin());
        float increment = value.getIncrement();
        newValue = Math.round(newValue / increment) * increment;

        value.setValue(newValue);
    }
}

