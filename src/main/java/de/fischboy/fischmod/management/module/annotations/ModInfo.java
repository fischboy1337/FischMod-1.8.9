package de.fischboy.fischmod.management.module.annotations;

import de.fischboy.fischmod.management.module.ModuleCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModInfo {

    String name();
    String description() default "Not described.";
    ModuleCategory category() default ModuleCategory.GAMEPLAY;
}
