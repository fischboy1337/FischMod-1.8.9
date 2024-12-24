package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class ModeSwitcher extends SubComponent<ModeValue> {

    public ModeSwitcher(ModeValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(50, 50, 50, 200));

        RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(100, 100, 100));

        String currentMode = value.getCurrentModes();
        String previousMode = value.getPreviousMode();
        String nextMode = value.getNextMode();

        fontRenderer.drawString(currentMode, (int) (x + width / 2 - fontRenderer.getStringWidth(currentMode) / 2),
                (int) (y + height / 2 - fontRenderer.FONT_HEIGHT / 2), Color.WHITE.getRGB());

        fontRenderer.drawString(previousMode, (int) (x + 10),
                (int) (y + height / 2 - fontRenderer.FONT_HEIGHT / 2), new Color(140, 140, 140).getRGB());

        fontRenderer.drawString(nextMode, (int) (x + width - fontRenderer.getStringWidth(nextMode) - 10),
                (int) (y + height / 2 - fontRenderer.FONT_HEIGHT / 2), new Color(140, 140, 140).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                value.nextMode();
            } else if (mouseButton == 1) {
                value.previousMode();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }
}
