package de.fischboy.fischmod.management.event.callables;

import lombok.Getter;

@Getter
public class EventCancellable extends Event {

    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

}