package de.fischboy.fischmod.management.event;

import de.fischboy.fischmod.management.event.callables.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@Getter @RequiredArgsConstructor
public class EventData {

    private final Object source;
    private final Method target;
    private final Class<? extends Event> eventType;
    private final int priority;
}
