package de.fischboy.fischmod.management.module.value;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.module.value.impl.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Value<T> {

    private final String name;
    private T value;

    public Value(String name, T value) {
        this.name = name;
        this.value = value;

        FischMod.INSTANCE.getModuleManager().getRecentlyAddedMod().getValues().add(this);
    }

    public boolean isCheckBox() {
        return this instanceof CheckBoxValue;
    }
    public boolean isSlider ()  {
        return this instanceof SliderValue;
    }
    public boolean isText ()  {
        return this instanceof TextValue;
    }
    public boolean isColor ()  {
        return this instanceof ColorValue;
    }
    public boolean isMode ()  {
        return this instanceof ModeValue;
    }
    public boolean isKeybind ()  {
        return this instanceof KeybindValue;
    }
}
