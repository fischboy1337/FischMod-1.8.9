package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TextComponent extends SubComponent<TextValue> {

    private boolean isFocused = false;
    private String currentText;

    public TextComponent(TextValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
        this.currentText = value.getValue();
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

        String textToDisplay = currentText.isEmpty() && !isFocused ? "Enter text..." : currentText;
        int textColor = currentText.isEmpty() && !isFocused ? new Color(150, 150, 150).getRGB() : Color.WHITE.getRGB();

        fontRenderer.drawString(textToDisplay, (int) (x + 10), (int) (y + height / 2 - fontRenderer.FONT_HEIGHT / 2), textColor);
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            isFocused = true;
        } else {
            isFocused = false;
            value.setValue(currentText);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!isFocused) {
            return;
        }

        if (keyCode == Keyboard.KEY_BACK) {
            if (!currentText.isEmpty()) {
                currentText = currentText.substring(0, currentText.length() - 1);
            }
        } else if (keyCode == Keyboard.KEY_RETURN) {
            value.setValue(currentText);
            isFocused = false;
        } else if (GuiScreen.isCtrlKeyDown() && keyCode == Keyboard.KEY_V) {
            currentText += GuiScreen.getClipboardString();
        } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            currentText += typedChar;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }
}
