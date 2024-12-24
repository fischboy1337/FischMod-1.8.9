package de.fischboy.fischmod.management.module;

import com.google.gson.JsonObject;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import de.fischboy.fischmod.api.utils.input.MouseUtil;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class HudModule extends Module {

    private final Bounds bounds = getClass().getAnnotation(Bounds.class);

    private float posX = bounds.posX();
    private float posY = bounds.posY();
    private float width = bounds.width();
    private float height = bounds.height();

    private float lastX;
    private float lastY;

    private ColorValue background;
    private SliderValue radius;

    public HudModule() {
        background = new ColorValue("Background", new Color(0, 0, 0, 150));
        radius = new SliderValue("Radius", 0, 10, 8, 1);
    }

    public abstract void render();

    public abstract void renderDummy();

    public void animateDrag(int mouseX, int mouseY) {
        this.posX = mouseX - lastX;
        this.posY = mouseY - lastY;
    }

    public void drag(int mouseX, int mouseY) {
        this.lastX = mouseX - posX;
        this.lastY = mouseY - posY;
    }

    public void setPosition(float x, float y) {
        this.setPosX(x);
        this.setPosY(y);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return MouseUtil.isMouseAt(mouseX, mouseY, posX, posY, bounds.width(), bounds.height());
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();

        json.addProperty("x", posX);
        json.addProperty("y", posY);

        return json;
    }

    @Override
    public void parseJson(JsonObject json) {
        super.parseJson(json);

        try {
            this.setPosX(json.get("x").getAsFloat());
            this.setPosY(json.get("y").getAsFloat());

        } catch (Exception e) {
            ConsoleUtil.error("Failed to parse configuration for '" + getInfo().name() + "'!");
            ConsoleUtil.error(e.getMessage());
        }
    }

}