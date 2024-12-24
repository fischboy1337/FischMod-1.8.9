package de.fischboy.fischmod.gui.clickGui;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import de.fischboy.fischmod.api.utils.input.MouseUtil;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.gui.clickGui.component.impl.ModButton;
import de.fischboy.fischmod.management.module.ModuleCategory;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClickGUI extends GuiScreen {

    private final List<ModButton> MODS = new ArrayList<>();
    private ModuleCategory selectedCategory = ModuleCategory.GAMEPLAY;

    private float windowWidth = 600;
    private float windowHeight = 400;
    private float halfWidth = width / 2;
    private float halfHeight = height / 2;

    private int modScroll;
    private final int scrollSpeed = 10;
    private final ModuleCategory[] categories = ModuleCategory.values();

    public ClickGUI() {
        float modWidth = windowWidth / 3 - 16;
        float modHeight = 50;

        FischMod.INSTANCE.getModuleManager().forEach(m -> MODS.add(new ModButton(m, this, 0, 0, modWidth, modHeight)));
    }

    @Override
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.halfWidth = (float) width / 2;
        this.halfHeight = (float) height / 2;

        this.handleScrolling();
        this.updateModPosition();
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawWindow();
        this.drawCategoryPanel(mouseX, mouseY);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.glScissor(halfWidth - windowWidth / 2, halfHeight - windowHeight / 2 + 35, windowWidth, windowHeight - 35);

        MODS.stream().filter(m -> m.getCategory() == selectedCategory && isInView(m.getY())).forEach(m -> {
            m.drawScreen(mouseX, mouseY);
        });
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private boolean isInView(float modY) {
        return modY > halfHeight - windowHeight / 2 + 35 && modY < halfHeight + windowHeight / 2;
    }

    public void drawWindow() {
        Color backgroundColor = new Color(50, 50, 50, 200);
        RenderRoundedUtil.drawRoundedRect(halfWidth - windowWidth / 2, halfHeight - windowHeight / 2, windowWidth, windowHeight, 10, backgroundColor);

        Color headerColor = new Color(55, 55, 55, 200);
        RenderRoundedUtil.drawRoundedRect(halfWidth - windowWidth / 2, halfHeight - windowHeight / 2, windowWidth, 35, 10, headerColor);
        RenderUtil.drawCenteredStringWithShadow(FischMod.NAME, halfWidth, halfHeight - windowHeight / 2 + 12, Color.WHITE.getRGB());
    }

    public void drawCategoryPanel(int mouseX, int mouseY) {
        float panelHeight = 30;
        float categorySpacing = 80;
        float startX = halfWidth - (categories.length * categorySpacing + 180) / 2;
        float panelY = halfHeight - windowHeight / 2 + 35;

        RenderRoundedUtil.drawRoundedRect(halfWidth - windowWidth / 2, panelY, windowWidth, panelHeight, 10, new Color(45, 45, 45, 220));

        for (int i = 0; i < categories.length; i++) {
            ModuleCategory category = categories[i];
            float categoryX = startX + i * categorySpacing;

            boolean isHovered = MouseUtil.isMouseAt(mouseX, mouseY, categoryX, panelY, categorySpacing, panelHeight);
            Color categoryColor = isHovered ? new Color(85, 170, 255) : Color.WHITE;
            int backgroundColor = category == selectedCategory ? new Color(30, 144, 255, 180).getRGB() : new Color(45, 45, 45, 180).getRGB();

            RenderRoundedUtil.drawRoundedRect(categoryX, panelY, categorySpacing - 10, panelHeight, 8, new Color(backgroundColor));
            RenderUtil.drawCenteredString(category.name(), categoryX + categorySpacing / 2 - 5, panelY + 8, categoryColor);
        }

        float cosmeticsX = startX + categories.length * categorySpacing + 20;
        float settingsX = cosmeticsX + categorySpacing + 10;

        drawAdditionalButton(mouseX, mouseY, cosmeticsX, panelY, categorySpacing, panelHeight, "Cosmetics");
        drawAdditionalButton(mouseX, mouseY, settingsX, panelY, categorySpacing, panelHeight, "Client Settings");
    }

    private void drawAdditionalButton(int mouseX, int mouseY, float x, float y, float width, float height, String text) {
        boolean isHovered = MouseUtil.isMouseAt(mouseX, mouseY, x, y, width, height);
        Color color = isHovered ? new Color(85, 170, 255) : Color.WHITE;
        RenderRoundedUtil.drawRoundedRect(x, y, width - 10, height, 8, new Color(45, 45, 45, 180));

        RenderUtil.drawCenteredString(text, x + width / 2 - 5, y + 8, color);
    }

    private void handleScrolling() {
        int wheel = Mouse.getDWheel();

        if (wheel != 0) {
            this.modScroll += Integer.signum(wheel) * scrollSpeed;
        }
        modScroll = Math.max(Math.min(modScroll, 0), -MODS.size() * 50 + (int) (windowHeight - 50));
    }

    private void updateModPosition() {
        int i = 0;
        int rowCount = 0;

        float modWidth = (windowWidth - 24) / 3;
        float modHeight = 50;

        for (ModButton mod : MODS) {
            if (mod.getCategory() != selectedCategory) {
                continue;
            }

            float modX = halfWidth - windowWidth + 12 + (i % 3) * (modWidth + 8);
            float modY = halfHeight - windowHeight + 40 + (rowCount * (modHeight + 5));

            mod.setX(modX + 295);
            mod.setY((modY + modScroll) + 250);

            i++;
            if (i % 3 == 0) {
                rowCount++;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        float categorySpacing = 80;
        float startX = halfWidth - (categories.length * categorySpacing + 180) / 2;
        float panelY = halfHeight - windowHeight / 2 + 35;

        for (int i = 0; i < categories.length; i++) {
            ModuleCategory category = categories[i];
            float categoryX = startX + i * categorySpacing;

            if (MouseUtil.isMouseAt(mouseX, mouseY, categoryX, panelY, categorySpacing, 30)) {
                selectedCategory = category;
                return;
            }
        }
        float cosmeticsX = startX + categories.length * categorySpacing + 20;
        if (MouseUtil.isMouseAt(mouseX, mouseY, cosmeticsX, panelY, categorySpacing, 30)) {
            ConsoleUtil.info("Cosmetics Clicked");
            return;
        }

        float settingsX = cosmeticsX + categorySpacing + 10;
        if (MouseUtil.isMouseAt(mouseX, mouseY, settingsX, panelY, categorySpacing, 30)) {
            ConsoleUtil.info("Settings Clicked");
            return;
        }

        if (isOverModArea(mouseX, mouseY)) {
            MODS.forEach(m -> {
                if (m.getCategory() == selectedCategory) {
                    m.mouseClicked(mouseX, mouseY, mouseButton);
                    m.mouseReleased(mouseX, mouseY, mouseButton);
                }
            });
        }
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
    }

    public boolean isOverModArea(int mouseX, int mouseY) {
        return MouseUtil.isMouseAt(mouseX, mouseY, halfWidth - windowWidth, halfHeight - windowHeight + 31, windowWidth * 2, windowHeight * 2 - 31);
    }
}
