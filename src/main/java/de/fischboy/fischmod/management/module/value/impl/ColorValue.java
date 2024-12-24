package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;

import java.awt.*;

public class ColorValue extends Value<Color> {

    public ColorValue(String name, Color value) {
        super(name, value);
    }

    public void setColor(int red, int green, int blue) {
        setValue(new Color(red, green, blue));
    }

    public int getRed() {
        return getValue().getRed();
    }

    public int getGreen() {
        return getValue().getGreen();
    }

    public int getBlue() {
        return getValue().getBlue();
    }
}
