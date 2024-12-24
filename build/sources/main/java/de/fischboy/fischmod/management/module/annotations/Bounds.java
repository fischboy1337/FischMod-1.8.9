package de.fischboy.fischmod.management.module.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Bounds {

    float posX();
    float posY();
    float width();
    float height();
}
