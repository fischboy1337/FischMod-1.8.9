package de.fischboy.fischmod.management.event;

import de.fischboy.fischmod.management.event.callables.Event;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

    private final CopyOnWriteArrayList<EventData> dataList = new CopyOnWriteArrayList<>();

    private final Comparator<EventData> prioritySort = Comparator.comparing(EventData::getPriority);

    @SuppressWarnings("unchecked")
    public void register(final Object obj) {
        for (final Method m : obj.getClass().getMethods()) {
            if (!isBadMethod(m)) {
                final Class<? extends Event> event = (Class<? extends Event>) m.getParameters()[0].getType();
                final EventTarget annot = m.getAnnotation(EventTarget.class);

                dataList.add(new EventData(obj, m, event, annot.priority()));

                dataList.sort(prioritySort);
            }
        }
    }

    public void unregister(final Object obj) {
        this.dataList.removeIf(data -> data.getSource() == obj);
    }

    public void clear() {
        this.dataList.clear();
    }

    public void call(final Event event) {
        for (final EventData data : dataList) {
            if (data.getEventType() == event.getClass()) {
                try {
                    data.getTarget().invoke(data.getSource(), event);
                } catch (final Throwable t) {
                    ConsoleUtil.error(t.toString());
                }
            }
        }
    }

    private boolean isBadMethod(final Method method) {
        return method.getAnnotations().length != 1 || !method.isAnnotationPresent(EventTarget.class) || method.getParameterCount() != 1;
    }
}
