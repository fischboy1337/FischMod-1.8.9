package de.fischboy.fischmod.management.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.config.impl.ModConfig;
import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import lombok.Getter;

import java.io.File;

@Getter
public class ConfigManager implements IMinecraft {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ModConfig modConfig;
    private File directory;

    public void onStart() {
        this.directory = new File(mc.mcDataDir, FischMod.NAME);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        this.modConfig = new ModConfig(new File(directory, "settings.json"), GSON);

        this.createModConfig();
    }

    public void createModConfig() {
        if (modConfig.getFile().exists()) {
            modConfig.load();
        }else {
            modConfig.save();
        }
    }
}
