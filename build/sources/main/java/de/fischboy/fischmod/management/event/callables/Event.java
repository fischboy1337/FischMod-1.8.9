package de.fischboy.fischmod.management.event.callables;

import de.fischboy.fischmod.FischMod;

public class Event {

    public void call() {
        FischMod.INSTANCE.getEventManager().call(this);
    }
}
