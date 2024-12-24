package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.KeybindValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeybindComponent extends SubComponent<KeybindValue> {

    private boolean isFocused = false;
    private String currentKeybind;

    public KeybindComponent(KeybindValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
        this.currentKeybind = KeyEvent.getKeyText(value.getValue());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(50, 50, 50, 200));

        if (isFocused) {
            RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(63, 150, 250));
        } else {
            RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(100, 100, 100));
        }

        String textToDisplay = currentKeybind.isEmpty() && !isFocused ? "Press a key..." : currentKeybind;
        int textColor = currentKeybind.isEmpty() && !isFocused ? new Color(150, 150, 150).getRGB() : Color.WHITE.getRGB();

        fontRenderer.drawString(textToDisplay, (int) (x + 10), (int) (y + height / 2 - fontRenderer.FONT_HEIGHT / 2), textColor);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        isFocused = isHovered(mouseX, mouseY) && mouseButton == 0;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!isFocused) {
            return;
        }

        if (keyCode != Keyboard.KEY_ESCAPE) {
            value.setValue(keyCode);
            currentKeybind = KeyEvent.getKeyText(keyCode);
            isFocused = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }
}
