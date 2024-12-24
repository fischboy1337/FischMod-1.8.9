package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;
import lombok.Getter;
import lombok.Setter;

public class SliderValue extends Value<Float> {

    @Getter
    @Setter
    private float min, max, increment;

    public SliderValue(String name, float min, float max, float value, float increment) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public void increase() {
        if (getValue() + increment <= max) {
            setValue(getValue() + increment);
        } else {
            setValue(max);
        }
    }

    public void decrease() {
        if (getValue() - increment >= min) {
            setValue(getValue() - increment);
        } else {
            setValue(min);
        }
    }
}
