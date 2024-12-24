package de.fischboy.fischmod.gui.elements;

import de.fischboy.fischmod.api.utils.input.MouseUtil;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class FischButton extends GuiButton {

    private int cornerRadius;
    private boolean shadow;

    public FischButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int cornerRadius) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.cornerRadius = cornerRadius;
        this.shadow = true;
    }

    public FischButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int cornerRadius, boolean shadow) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.cornerRadius = cornerRadius;
        this.shadow = shadow;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = MouseUtil.isMouseAt(mouseX, mouseY, xPosition, yPosition, width, height);

        Color buttonColorWithShadow = this.hovered ? new Color(60, 180, 255) : new Color(50, 50, 50, 180);
        Color buttonColor = this.hovered ? new Color(60, 180, 255) : new Color(50, 50, 50, 200);
        Color shadowColor = new Color(0, 0, 0, 100);

        if (shadow) {
            RenderRoundedUtil.drawRoundedRectWithShadow(xPosition, yPosition, width, height, cornerRadius, buttonColorWithShadow, shadowColor, 3.0f);
        } else {
            RenderRoundedUtil.drawRoundedRect(xPosition, yPosition, width, height, cornerRadius, buttonColor);
        }

        Color textColor = this.hovered ? new Color(255, 255, 255) : new Color(200, 200, 200);
        RenderUtil.drawCenteredString(this.displayString, this.xPosition + (float) this.width / 2, this.yPosition + (float) (this.height - 8) / 2, textColor);
    }
}