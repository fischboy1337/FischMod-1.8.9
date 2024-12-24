package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;

public class TextValue extends Value<String> {
    public TextValue(String name, String value) {
        super(name, value);
    }

    public void setText(String newText) {
        setValue(newText);
    }
}
