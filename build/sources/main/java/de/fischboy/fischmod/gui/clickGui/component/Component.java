package de.fischboy.fischmod.gui.clickGui.component;

import de.fischboy.fischmod.gui.clickGui.ClickGUI;
import de.fischboy.fischmod.api.utils.input.MouseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class Component {
    protected ClickGUI parent;
    protected float x, y, width, height;

    public abstract void drawScreen(int mouseX, int mouseY);
    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);
    public abstract void keyTyped(char typedChar, int keyCode);

    public boolean isHovered(int mouseX, int mouseY) {
        return MouseUtil.isMouseAt(mouseX, mouseY, x, y, width, height);
    }
}