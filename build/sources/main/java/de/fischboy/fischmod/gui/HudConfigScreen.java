package de.fischboy.fischmod.gui;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.gui.clickGui.ClickGUI;
import de.fischboy.fischmod.gui.elements.FischButton;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.Module;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class HudConfigScreen extends GuiScreen {

    private ClickGUI clickGUI;
    private HudModule draggingModule;
    private HudModule resizingModule;
    private boolean isResizing;

    @Override
    public void initGui() {
        draggingModule = null;
        resizingModule = null;
        isResizing = false;
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
        this.buttonList.add(new FischButton(0, width / 2 - 60, height / 2 - 10, 120, 20, "Mods", 5));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (Module module : FischMod.INSTANCE.getModuleManager()) {
            if (module.isHud() && module.isEnabled()) {
                HudModule hudModule = (HudModule) module;

                if (draggingModule == hudModule) {
                    hudModule.animateDrag(mouseX, mouseY);
                }
                hudModule.renderDummy();

                float width = hudModule.getBounds().width();
                float height = hudModule.getBounds().height();
                float posX = hudModule.getPosX();
                float posY = hudModule.getPosY();
                float borderThickness = 1f;
                RenderUtil.drawBorderRect(posX, posY, width, height, borderThickness, new Color(255, 255, 255));


                float cornerSize = 3.0f;
                RenderUtil.drawRect(posX - cornerSize, posY - cornerSize, cornerSize, cornerSize, new Color(0, 225, 255));
                RenderUtil.drawRect(posX + width, posY - cornerSize, cornerSize, cornerSize, new Color(0, 225, 255));
                RenderUtil.drawRect(posX - cornerSize, posY + height, cornerSize, cornerSize, new Color(0, 225, 255));
                RenderUtil.drawRect(posX + width, posY + height, cornerSize, cornerSize, new Color(0, 225, 255));

                if (isResizing && resizingModule == hudModule) {
                    float newWidth = Math.max(30, mouseX - posX);
                    float newHeight = Math.max(30, mouseY - posY);
                    hudModule.setSize(newWidth, newHeight);
                    ConsoleUtil.info("Width: " + hudModule.getWidth());
                    ConsoleUtil.info("Height: " + hudModule.getWidth());
                    ConsoleUtil.info("New Width: " + newWidth);
                    ConsoleUtil.info("New Height: " + newHeight);
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == 0) {
            if (clickGUI == null) {
                mc.entityRenderer.stopUseShader();
                clickGUI = new ClickGUI();
            }
            mc.displayGuiScreen(clickGUI);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (draggingModule == null && !isResizing) {
            resizingModule = (HudModule) FischMod.INSTANCE.getModuleManager()
                    .findFirst(m -> m.isHud() && m.isEnabled() && isCornerClicked((HudModule) m, mouseX, mouseY));

            if (resizingModule != null) {
                ConsoleUtil.info("Corner Clicked");
                isResizing = true;
            } else {
                draggingModule = (HudModule) FischMod.INSTANCE.getModuleManager()
                        .findFirst(m -> m.isHud() && m.isEnabled() && ((HudModule) m).isHovered(mouseX, mouseY));

                if (draggingModule != null) {
                    draggingModule.drag(mouseX, mouseY);
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (draggingModule != null) {
            FischMod.INSTANCE.getConfigManager().getModConfig().save();
        }
        this.draggingModule = null;
        this.resizingModule = null;
        this.isResizing = false;
    }

    private boolean isCornerClicked(HudModule module, int mouseX, int mouseY) {
        float posX = module.getPosX();
        float posY = module.getPosY();
        float width = module.getBounds().width();
        float height = module.getBounds().height();
        float cornerSize = 3.0f;


        return (mouseX >= posX - cornerSize && mouseX <= posX && mouseY >= posY - cornerSize && mouseY <= posY) ||
                (mouseX >= posX + width && mouseX <= posX + width + cornerSize && mouseY >= posY - cornerSize && mouseY <= posY) ||
                (mouseX >= posX - cornerSize && mouseX <= posX && mouseY >= posY + height && mouseY <= posY + height + cornerSize) ||
                (mouseX >= posX + width && mouseX <= posX + width + cornerSize && mouseY >= posY + height && mouseY <= posY + height + cornerSize);
    }


    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
    }
}
