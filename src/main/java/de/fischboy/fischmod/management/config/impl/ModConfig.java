package de.fischboy.fischmod.management.config.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.config.Config;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.api.utils.io.FileUtil;

import java.io.File;
import java.util.Map;

public class ModConfig extends Config {

    public ModConfig(File file, Gson gson) {
        super(file, gson);
    }

    private JsonObject modsToJson() {
        JsonObject json = new JsonObject();

        for (Module mod : FischMod.INSTANCE.getModuleManager()) {
            json.add(mod.getInfo().name(), mod.toJson());
        }
        return json;
    }


    @Override
    public void save() {
        FileUtil.write(gson.toJson(modsToJson()), file);
    }

    @Override
    public void load() {
        JsonObject json = (JsonObject) new JsonParser().parse(FileUtil.read(file));

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            Module mod = FischMod.INSTANCE.getModuleManager().getModByName(entry.getKey());

            if(mod == null) {
                continue;
            }

            mod.parseJson((JsonObject) entry.getValue());
        }
    }
}
