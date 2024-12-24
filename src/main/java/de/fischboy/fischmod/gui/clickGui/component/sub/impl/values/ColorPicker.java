package de.fischboy.fischmod.gui.clickGui.component.sub.impl.values;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import lombok.Getter;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ColorPicker extends SubComponent<ColorValue> {

    private boolean isDraggingColor = false;
    private boolean isDraggingHue = false;
    private boolean isDraggingAlpha = false;

    @Getter
    private boolean expanded = false;
    private int selectedColor;
    private float hue = 0.0f;
    private float saturation = 1.0f;
    private float brightness = 1.0f;
    private float alpha = 1.0f;

    private final float previewSize = 25;
    private final float expandedWidth = 150;
    private final float expandedHeight = 150;
    private final float indicatorSize = 5;

    public ColorPicker(ColorValue value, float x, float y, float width, float height) {
        super(value, x, y, width, height);
        this.selectedColor = value.getValue().getRGB();
        this.alpha = value.getValue().getAlpha() / 255f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (expanded) {
            drawExpandedColorPicker(mouseX, mouseY);
        } else {
            drawColorPreview();
        }
    }

    private void drawExpandedColorPicker(int mouseX, int mouseY) {
        drawColorSquare();
        drawHueBar();
        drawAlphaBar();
        drawHueIndicator();
        drawColorSquareIndicator();
        drawAlphaIndicator();
        handleDragging(mouseX, mouseY);
    }

    private void drawColorPreview() {
        RenderUtil.drawCenteredString(value.getName(), this.x + (previewSize * 3), this.y + (previewSize / 2), Color.white);
        RenderUtil.drawRect(this.x, this.y, previewSize, previewSize, new Color(selectedColor));
        RenderUtil.drawBorderRect(this.x, this.y, previewSize, previewSize, 1, new Color(176, 176, 176));
    }

    private void drawAlphaBar() {
        float alphaBarX = this.x + expandedWidth + 25;
        for (int y = 0; y < expandedHeight; y++) {
            float alphaValue = 1.0f - (float) y / expandedHeight;
            Color color = new Color((selectedColor & 0xFFFFFF) | ((int) (alphaValue * 255) << 24), true);
            RenderUtil.drawRect(alphaBarX, this.y + y, 10, 1, color);
        }
        RenderUtil.drawBorderRect(alphaBarX, this.y, 10, expandedHeight, 1, Color.white);
    }


    private void drawColorSquare() {
        for (int x = 0; x < expandedWidth; x++) {
            for (int y = 0; y < expandedHeight; y++) {
                float saturation = x / expandedWidth;
                float brightness = 1 - (y / expandedHeight);
                Color color = Color.getHSBColor(hue, saturation, brightness);
                RenderUtil.drawRect(this.x + x, this.y + y, 1, 1, color);
            }
        }
    }

    private void drawHueBar() {
        float hueBarX = this.x + expandedWidth + 10;
        for (int y = 0; y < expandedHeight; y++) {
            float hue = (float) y / expandedHeight;
            Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
            RenderUtil.drawRect(hueBarX, this.y + y, 10, 1, color);
        }
    }

    private void handleDragging(int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0)) {
            if (isHovered(mouseX, mouseY)) {
                if (expanded) {
                    if (mouseX < this.x + expandedWidth) {
                        isDraggingColor = true;
                    } else if (mouseX > this.x + expandedWidth + 10 && mouseX < this.x + expandedWidth + 20) {
                        isDraggingHue = true;
                    } else if (mouseX > this.x + expandedWidth + 25 && mouseX < this.x + expandedWidth + 35) {
                        isDraggingAlpha = true;
                    }
                }
            }
        } else {
            isDraggingColor = false;
            isDraggingHue = false;
            isDraggingAlpha = false;
        }

        if (isDraggingColor) {
            float relativeX = Math.max(0, Math.min(mouseX - this.x, expandedWidth));
            float relativeY = Math.max(0, Math.min(mouseY - this.y, expandedHeight));
            saturation = relativeX / expandedWidth;
            brightness = 1 - (relativeY / expandedHeight);
            updateColor();
        }

        if (isDraggingHue) {
            float relativeY = Math.max(0, Math.min(mouseY - this.y, expandedHeight));
            hue = relativeY / expandedHeight;
            updateColor();
        }

        if (isDraggingAlpha) {
            float relativeY = Math.max(0, Math.min(mouseY - this.y, expandedHeight));
            alpha = 1.0f - (relativeY / expandedHeight);
            updateColor();
        }
    }

    private void updateColor() {
        selectedColor = Color.getHSBColor(hue, saturation, brightness).getRGB();
        selectedColor = (selectedColor & 0xFFFFFF) | ((int) (alpha * 255) << 24);
        value.setValue(new Color(selectedColor, true));
    }

    private void drawHueIndicator() {
        float hueBarX = this.x + expandedWidth + 10;
        float indicatorY = this.y + (hue * expandedHeight) - (indicatorSize / 2);
        float arrowWidth = 10;
        float arrowHeight = 5;

        RenderUtil.drawArrow(hueBarX, indicatorY, arrowWidth, arrowHeight, Color.white, "r");
    }

    private void drawColorSquareIndicator() {
        float indicatorX = this.x + (saturation * expandedWidth) - (indicatorSize / 2);
        float indicatorY = this.y + ((1 - brightness) * expandedHeight) - (indicatorSize / 2);

        RenderRoundedUtil.drawCircleOutline(indicatorX, indicatorY, 4, 1.5f, Color.white);
    }

    private void drawAlphaIndicator() {
        float alphaBarX = this.x + expandedWidth + 25;
        float indicatorY = this.y + ((1.0f - alpha) * expandedHeight) - (indicatorSize / 2);
        RenderUtil.drawArrow(alphaBarX, indicatorY, 10, 5, Color.white, "r");
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (expanded) {
                handleDragging(mouseX, mouseY);
            } else {
                expanded = true;
            }
        } else {
            expanded = false;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isDraggingColor = false;
        isDraggingHue = false;
        isDraggingAlpha = false;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        float widthToCheck = expanded ? expandedWidth + 35 : previewSize;
        float heightToCheck = expanded ? expandedHeight : previewSize;
        return mouseX >= this.x && mouseX <= this.x + widthToCheck &&
                mouseY >= this.y && mouseY <= this.y + heightToCheck;
    }
}