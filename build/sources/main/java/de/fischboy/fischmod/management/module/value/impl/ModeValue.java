package de.fischboy.fischmod.management.module.value.impl;

import de.fischboy.fischmod.management.module.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ModeValue extends Value<String> {

    private final List<String> modes;
    private int currentModeIndex;

    public ModeValue(String name, List<String> modes, String currentMode) {
        super(name, modes.get(0));
        this.modes = new ArrayList<>(modes);
        this.currentModeIndex = modes.indexOf(currentMode);
        setValue(modes.get(this.currentModeIndex));
    }

    public ModeValue(String name, List<String> modes) {
        super(name, modes.get(0));
        this.modes = new ArrayList<>(modes);
        this.currentModeIndex = 0;
    }

    public void nextMode() {
        currentModeIndex = (currentModeIndex + 1) % modes.size();
        setValue(modes.get(currentModeIndex));
    }

    public void previousMode() {
        currentModeIndex = (currentModeIndex - 1 + modes.size()) % modes.size();
        setValue(modes.get(currentModeIndex));
    }

    public String getPreviousMode() {
        int previousIndex = (currentModeIndex - 1 + modes.size()) % modes.size();
        return modes.get(previousIndex);
    }

    public String getNextMode() {
        int nextIndex = (currentModeIndex + 1) % modes.size();
        return modes.get(nextIndex);
    }

    public String getCurrentModes() {
        return modes.get(currentModeIndex);
    }
}
