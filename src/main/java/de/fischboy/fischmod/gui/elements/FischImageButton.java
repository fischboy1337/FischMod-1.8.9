package de.fischboy.fischmod.gui.elements;

import de.fischboy.fischmod.api.utils.input.MouseUtil;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class FischImageButton extends GuiButton {

    private final ResourceLocation imageResource;
    private final int imageWidth;
    private final int imageHeight;
    private int cornerRadius;
    private boolean shadow;

    public FischImageButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation imageResource, int imageWidth, int imageHeight, int cornerRadius) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.imageResource = imageResource;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.cornerRadius = cornerRadius;
        this.shadow = true;
    }

    public FischImageButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation imageResource, int imageWidth, int imageHeight, int cornerRadius, boolean shadow) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.imageResource = imageResource;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
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

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        mc.getTextureManager().bindTexture(imageResource);
        Gui.drawModalRectWithCustomSizedTexture(xPosition + (width - imageWidth) / 2, yPosition + (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && this.isMouseOver();
    }

    @Override
    public boolean isMouseOver() {
        return this.hovered;
    }
}
