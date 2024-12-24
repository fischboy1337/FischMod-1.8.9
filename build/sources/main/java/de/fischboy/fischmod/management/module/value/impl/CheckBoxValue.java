package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;

public class CheckBoxValue extends Value<Boolean> {

    public CheckBoxValue(String name) {
        super(name, false);
    }

    public CheckBoxValue(String name, Boolean value) {
        super(name, value);
    }
}
