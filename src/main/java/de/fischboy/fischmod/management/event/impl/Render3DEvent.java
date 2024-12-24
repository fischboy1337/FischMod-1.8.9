package de.fischboy.fischmod.management.event.impl;

import de.fischboy.fischmod.management.event.callables.Event;
import lombok.Getter;

public class Render3DEvent extends Event {

    @Getter
    private float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
