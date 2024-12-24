package de.fischboy.fischmod.management.module.impl.hud;

import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.TextValue;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Bounds(posX = 20, posY = 20, width = 100, height = 30)
@ModInfo(name = "CPS", description = "Shows your Clicks", category = ModuleCategory.HUD)
public class CPSMod extends HudModule {

    public TextValue text = new TextValue("Text", "%lmb% | %rmb%");

    private static final java.util.List<Long> lmbClicks = new ArrayList<>();
    private static final List<Long> rmbClicks = new ArrayList<>();
    private static boolean lmbWasPressed, rmbWasPressed;

    @Override
    public void render() {
        RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
        String textToRender = formatText(text.getValue().toLowerCase());
        float centerX = this.getPosX() + this.getBounds().width() / 2;
        float centerY = (this.getPosY() + this.getBounds().height() / 2) - (float) mc.fontRendererObj.FONT_HEIGHT / 2;
        RenderUtil.drawCenteredString(textToRender, centerX, centerY, Color.WHITE);
        getCPS();
    }

    private String formatText(String text) {
        text = text.replace("%lmb%", String.valueOf(getLMB()));
        text = text.replace("%rmb%", String.valueOf(getRMB()));
        return text;
    }

    private void getCPS() {
        final boolean lmbPressed = Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown();

        if (lmbPressed != lmbWasPressed) {
            long lmbLastPressed = System.currentTimeMillis();
            lmbWasPressed = lmbPressed;
            if (lmbPressed) {
                lmbClicks.add(lmbLastPressed);
            }
        }

        final boolean rmbPressed = Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown();

        if (rmbPressed != rmbWasPressed) {
            long rmbLastPressed = System.currentTimeMillis();
            rmbWasPressed = rmbPressed;
            if (rmbPressed) {
                rmbClicks.add(rmbLastPressed);
            }
        }
    }

    public static int getLMB() {
        final long time = System.currentTimeMillis();
        lmbClicks.removeIf(aLong -> aLong + 1000 < time);
        return lmbClicks.size();
    }

    public static int getRMB() {
        final long time = System.currentTimeMillis();
        rmbClicks.removeIf(aLong -> aLong + 1000 < time);
        return rmbClicks.size();
    }

    @Override
    public void renderDummy() {
        this.render();
    }
}
