package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;

import java.awt.event.KeyEvent;

public class KeybindValue extends Value<Integer> {

    public KeybindValue(String name, Integer value) {
        super(name, value);
    }

    public String getKeybindAsString() {
        return KeyEvent.getKeyText(getValue());
    }
}
