package de.fischboy.fischmod.gui.clickGui.component.impl;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.gui.clickGui.ClickGUI;
import de.fischboy.fischmod.gui.clickGui.component.Component;
import de.fischboy.fischmod.gui.clickGui.value.ClickGUIValues;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ModButton extends Component {
    private final Module module;

    public ModButton(Module module, ClickGUI parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        this.module = module;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Color buttonColor = isHovered(mouseX, mouseY) ? new Color(80, 120, 160) : new Color(60, 90, 120);
        RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, buttonColor);

        if (module.isEnabled()) {
            RenderRoundedUtil.drawRoundedRect(x, y, width, height, 6, new Color(100, 200, 255, 100));
        }
        RenderUtil.drawCenteredStringWithShadow(module.getInfo().name(), x + width / 2, y + height / 2 - 4, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1 && !module.getValues().isEmpty()) {
                Minecraft.getMinecraft().entityRenderer.stopUseShader();
                Minecraft.getMinecraft().displayGuiScreen(new ClickGUIValues(parent, module));
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    public ModuleCategory getCategory() {
        return module.getInfo().category();
    }
}