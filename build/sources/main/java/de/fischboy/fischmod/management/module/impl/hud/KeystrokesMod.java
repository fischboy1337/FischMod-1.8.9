package de.fischboy.fischmod.management.module.impl.hud;

import de.fischboy.fischmod.api.utils.animation.DeltaTime;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import de.fischboy.fischmod.management.module.value.impl.ModeValue;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bounds(posX = 20, posY = 20, width = 60, height = 72)
@ModInfo(name = "Keystrokes", description = "KeyMod halt", category = ModuleCategory.HUD)
public class KeystrokesMod extends HudModule {

    public ColorValue keyPressedBackground = new ColorValue("Pressed Background", new Color(255, 255, 255));
    public ColorValue keyText = new ColorValue("Text", new Color(255, 255, 255));
    public ColorValue keyPressedText = new ColorValue("Pressed Text", new Color(0, 0, 0));
    public ModeValue modeValue = new ModeValue("Mode", Arrays.asList("WASD", "WASD_Mouse", "WASD_Jump", "WASD_Jump_Mouse"), "WASD_Jump_Mouse");

    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();

    @Override
    public void render() {
        GL11.glPushMatrix();

        FontRenderer fr = mc.fontRendererObj;
        KeystrokesMode mode = getKeystrokesMode(modeValue.getValue());
        updateCPS();

        float posX = this.getPosX();
        float posY = this.getPosY();

        for (Key key : mode.getKeys()) {
            int fadeTime = 20;
            if (key.isDown()) {
                key.fade += (1F / (float) fadeTime) * (DeltaTime.getDeltaTime() * 0.1f);
            } else {
                key.fade -= (1F / (float) fadeTime) * (DeltaTime.getDeltaTime() * 0.1f);
            }
            key.fade = Math.max(0, Math.min(1, key.fade));

            Color backgroundColor;
            Color textColor;

            if (key.isDown()) {
                backgroundColor = keyPressedBackground.getValue();
                textColor = keyPressedText.getValue();
            } else {
                backgroundColor = getBackground().getValue();
                textColor = keyText.getValue();
            }

            float x = posX + key.getX();
            float y = posY + key.getY();
            float width = key.getWidth();
            float height = key.getHeight();


            RenderRoundedUtil.drawRoundedRect(
                    x, y, width, height, getRadius().getValue().intValue(),
                    backgroundColor
            );

            if (!key.getName().contains("-")) {
                RenderUtil.drawCenteredString(
                        key.getName(),
                        x + width / 2,
                        y + height / 2 - 3,
                        textColor
                );
            } else {
                RenderUtil.drawCenteredString(
                        key.getName(),
                        x + width / 2,
                        y + height / 2 - 4,
                        textColor
                );

                if (key == Key.LMB) {
                    renderCPS(fr, leftClicks, key, this.getPosX(), this.getPosY());
                } else if (key == Key.RMB) {
                    renderCPS(fr, rightClicks, key, this.getPosX(), this.getPosY());
                }
            }
        }

        GL11.glPopMatrix();
    }

    private void updateCPS() {
        long currentTime = System.currentTimeMillis();

        leftClicks.removeIf(click -> (currentTime - click) > 1000);
        rightClicks.removeIf(click -> (currentTime - click) > 1000);

        if (Mouse.isButtonDown(0)) {
            leftClicks.add(currentTime);
        }
        if (Mouse.isButtonDown(1)) {
            rightClicks.add(currentTime);
        }
    }

    private void renderCPS(FontRenderer fr, List<Long> clicks, Key key, float posX, float posY) {
        String cpsText = clicks.size() + " CPS";
        fr.drawStringWithShadow(
                cpsText,
                posX + key.getX() + key.getWidth() / 2 - fr.getStringWidth(cpsText) / 2,
                posY + key.getY() + key.getHeight() + 2,
                Color.WHITE.getRGB()
        );
    }

    private KeystrokesMode getKeystrokesMode(String mode) {
        switch (mode) {
            case "WASD":
                return KeystrokesMode.WASD;
            case "WASD_Mouse":
                return KeystrokesMode.WASD_MOUSE;
            case "WASD_Jump":
                return KeystrokesMode.WASD_JUMP;
            case "WASD_Jump_Mouse":
            default:
                return KeystrokesMode.WASD_JUMP_MOUSE;
        }
    }

    @Override
    public void renderDummy() {
        this.render();
    }

    public static class Key {
        public static Minecraft mc = Minecraft.getMinecraft();

        private static final Key W = new Key("W", mc.gameSettings.keyBindForward, 21, 1, 18, 18);
        private static final Key A = new Key("A", mc.gameSettings.keyBindLeft, 1, 21, 18, 18);
        private static final Key S = new Key("S", mc.gameSettings.keyBindBack, 21, 21, 18, 18);
        private static final Key D = new Key("D", mc.gameSettings.keyBindRight, 41, 21, 18, 18);

        private static final Key LMB = new Key("LMB", mc.gameSettings.keyBindAttack, 1, 41, 28, 18);
        private static final Key RMB = new Key("RMB", mc.gameSettings.keyBindUseItem, 31, 41, 28, 18);

        private static final Key Jump1 = new Key("§m---", mc.gameSettings.keyBindJump, 1, 41, 58, 10);
        private static final Key Jump2 = new Key("§m---", mc.gameSettings.keyBindJump, 1, 61, 58, 10);

        @Getter
        private final String name;
        private final KeyBinding keyBind;
        @Getter
        private final int x, y, width, height;

        private float fade;

        public Key(String name, KeyBinding keyBind, int x, int y, int w, int h) {
            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }

        public boolean isDown() {
            return keyBind.isKeyDown();
        }
    }

    public enum KeystrokesMode {
        WASD(Key.W, Key.A, Key.S, Key.D),
        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, Key.Jump1),
        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, Key.Jump2);

        private final Key[] keys;
        private int width, height;

        KeystrokesMode(Key... keysIn) {
            this.keys = keysIn;

            for (Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public Key[] getKeys() {
            return keys;
        }
    }
}