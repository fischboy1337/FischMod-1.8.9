package de.fischboy.fischmod.management.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter @Setter @RequiredArgsConstructor
public abstract class Config {

    protected final File file;
    protected final Gson gson;

    public abstract void save();
    public abstract void load();
}
