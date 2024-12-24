package de.fischboy.fischmod.api.utils.input;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MouseUtil {

    public static boolean isMouseAt(int mouseX, int mouseY, float x, float y, float width, float height) {
        return (mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height);
    }

}