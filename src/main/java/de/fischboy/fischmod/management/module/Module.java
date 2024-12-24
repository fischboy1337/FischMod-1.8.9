package de.fischboy.fischmod.management.module;

import com.google.gson.JsonObject;
import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.Value;
import de.fischboy.fischmod.management.module.value.impl.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Module implements IMinecraft {

    private final List<Value<?>> values = new ArrayList<>();

    private ModInfo info = this.getClass().getAnnotation(ModInfo.class);

    private boolean enabled;

    public Module() {
        FischMod.INSTANCE.getModuleManager().setRecentlyAddedMod(this);
    }

    public boolean isHud() {
        return this.getClass().getAnnotation(Bounds.class) != null;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void toggle() {
        this.setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

        if (FischMod.INSTANCE.getConfigManager() != null) {
            FischMod.INSTANCE.getConfigManager().getModConfig().save();
        }
    }

    public JsonObject toJson() {
        JsonObject valuesJson = new JsonObject();

        /* TODO: When adding new values, copy what i did here
         *       with check box but the value type and value return type instead */
        for (Value<?> value : values) {
            if (value.isCheckBox()) {
                valuesJson.addProperty(value.getName(), (Boolean) value.getValue());
            }
            if (value.isSlider()) {
                valuesJson.addProperty(value.getName(), (Float) value.getValue());
            }
            if (value.isText()) {
                valuesJson.addProperty(value.getName(), (String) value.getValue());
            }
            if (value.isColor()) {
                Color color = (Color) value.getValue();
                String colorHex = String.format("#%06X", (0xFFFFFF & color.getRGB()));
                valuesJson.addProperty(value.getName(), colorHex);
            }
            if (value.isMode()) {
                valuesJson.addProperty(value.getName(), (String) value.getValue());
            }
        }

        JsonObject json = new JsonObject();

        json.add("values", valuesJson);
        json.addProperty("enabled", isEnabled());

        return json;
    }

    public void parseJson(JsonObject json) {
        JsonObject valuesJson = json.get("values").getAsJsonObject();

        /* TODO: When adding new values, copy what i did here
         *       with check box but the value type and value return type instead */
        for (Value<?> value : values) {
            if (value.isCheckBox()) {
                ((CheckBoxValue) value).setValue(valuesJson.get(value.getName()).getAsBoolean());
            }
            if (value.isSlider()) {
                ((SliderValue) value).setValue(valuesJson.get(value.getName()).getAsFloat());
            }
            if (value.isText()) {
                ((TextValue) value).setValue(valuesJson.get(value.getName()).getAsString());
            }
            if (value.isColor()) {
                String colorHex = valuesJson.get(value.getName()).getAsString();
                Color color = Color.decode(colorHex);
                ((ColorValue) value).setValue(color);
            }
            if (value.isMode()) {
                valuesJson.addProperty(value.getName(), (String) value.getValue());
                ((ModeValue) value).setValue(valuesJson.get(value.getName()).getAsString());
            }
        }
        this.setEnabled(json.get("enabled").getAsBoolean());
    }
}
