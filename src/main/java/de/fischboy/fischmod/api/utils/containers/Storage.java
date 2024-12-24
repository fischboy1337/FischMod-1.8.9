package de.fischboy.fischmod.api.utils.containers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Storage<T> extends CopyOnWriteArrayList<T> {

    public abstract void onStart();

    public T getByClass(Class<? extends T> clazz) {
        return this.findFirst(e -> e.getClass() == clazz);
    }

    public T findFirst(Predicate<T> predicate) {
        return this.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<T> collectIf(Predicate<T> predicate) {
        return this.stream().filter(predicate).collect(Collectors.toList());
    }

    @SafeVarargs
    public final void addAll(Supplier<T>... suppliers) {
        Stream.of(suppliers).map(Supplier::get).forEach(this::addIfAbsent);
    }
}
