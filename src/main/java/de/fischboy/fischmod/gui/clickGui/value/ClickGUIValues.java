package de.fischboy.fischmod.gui.clickGui.value;

import de.fischboy.fischmod.api.utils.input.MouseUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.gui.clickGui.ClickGUI;
import de.fischboy.fischmod.gui.clickGui.component.sub.SubComponent;
import de.fischboy.fischmod.gui.clickGui.component.sub.impl.values.TextComponent;
import de.fischboy.fischmod.gui.clickGui.component.sub.impl.values.*;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.value.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUIValues extends GuiScreen {

    private final ClickGUI clickGUI;
    private final Module module;

    private float valueScroll;
    private final List<SubComponent<?>> VALUES = new ArrayList<>();

    public ClickGUIValues(ClickGUI clickGUI, Module module) {
        this.clickGUI = clickGUI;
        this.module = module;

        module.getValues().forEach(v -> {
            float compWidth = clickGUI.getWindowWidth() - 24;
            float compHeight = 16;

            /* TODO: When adding new values, copy what i did here
             *       with check box but the value type and value return type instead */
            if (v.isCheckBox()) {
                VALUES.add(new CheckBox((CheckBoxValue) v, 0, 0, compWidth, compHeight));
            }
            if (v.isSlider()) {
                VALUES.add(new Slider((SliderValue) v, 0, 0, compWidth, compHeight));
            }
            if (v.isColor()) {
                VALUES.add(new ColorPicker((ColorValue) v, 0, 0, 35, 25));
            }
            if (v.isText()) {
                VALUES.add(new TextComponent((TextValue) v, 0, 0, compWidth, compHeight));
            }
            if (v.isMode()) {
                VALUES.add(new ModeSwitcher((ModeValue) v, 0, 0, compWidth, compHeight));
            }
            if (v.isKeybind()) {
                VALUES.add(new KeybindComponent((KeybindValue) v, 0, 0, compWidth, compHeight));
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        clickGUI.setHalfWidth((float) width / 2);
        clickGUI.setHalfHeight((float) height / 2);

        this.handleScrolling();
        this.updateValuePosition();
        clickGUI.drawWindow();

        float backX = clickGUI.getHalfWidth() + (clickGUI.getWindowWidth() / 2) - 20;
        float backY = clickGUI.getHalfHeight() - (clickGUI.getWindowHeight() / 2) + 15;

        RenderUtil.drawCenteredString(
                "<-",
                backX,
                backY,
                isOverBack(mouseX, mouseY) ? new Color(85, 170, 255) : new Color(255, 255, 255)
        );

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.glScissor(
                clickGUI.getHalfWidth() - clickGUI.getWindowWidth(),
                clickGUI.getHalfHeight() - clickGUI.getWindowHeight() + 31,
                clickGUI.getWindowWidth() * 2,
                clickGUI.getWindowHeight() * 2 - 31
        );
        VALUES.forEach(v -> v.drawScreen(mouseX, mouseY));
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (Mouse.isButtonDown(0)) {
            VALUES.forEach(v -> {
                if (v instanceof Slider) {
                    ((Slider) v).mouseDragged(mouseX, mouseY);
                }
            });
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateValuePosition() {
        int i = 0;
        for (SubComponent<?> value : VALUES) {
            float valueX = clickGUI.getHalfWidth() - (clickGUI.getWindowWidth() / 2) + 12;
            float valueY = clickGUI.getHalfHeight() - clickGUI.getWindowHeight() / 2 + 36 + i;

            value.setX(valueX);
            value.setY(valueY + valueScroll);

            if (value instanceof ColorPicker && ((ColorPicker) value).isExpanded()) {
                i += 160;
            } else {
                i += 30;
            }
        }
    }

    private void handleScrolling() {
        int wheel = Mouse.getDWheel();
        int amount = 10;

        if (wheel > 0) {
            this.valueScroll += amount;
        } else if (wheel < 0) {
            this.valueScroll -= amount;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (clickGUI.isOverModArea(mouseX, mouseY)) {
            VALUES.forEach(v -> v.mouseClicked(mouseX, mouseY, mouseButton));
        }
        if (isOverBack(mouseX, mouseY)) {
            Minecraft.getMinecraft().displayGuiScreen(clickGUI);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        VALUES.forEach(v -> v.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        VALUES.forEach(v -> v.keyTyped(typedChar, keyCode));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        Minecraft.getMinecraft().displayGuiScreen(this);
        super.actionPerformed(button);
    }

    private boolean isOverBack(int mouseX, int mouseY) {
        float backX = clickGUI.getHalfWidth() + (clickGUI.getWindowWidth() / 2) - 20;
        float backY = clickGUI.getHalfHeight() - (clickGUI.getWindowHeight() / 2) + 15;
        float width = 20;
        float height = 20;

        return MouseUtil.isMouseAt(mouseX, mouseY, backX - width / 2, backY - height / 2, width, height);
    }
}