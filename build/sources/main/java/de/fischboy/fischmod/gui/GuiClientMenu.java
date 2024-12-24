package de.fischboy.fischmod.gui;

import java.awt.*;
import java.io.IOException;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.visual.ClientFont;
import de.fischboy.fischmod.gui.elements.FischButton;
import de.fischboy.fischmod.gui.elements.FischImageButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class GuiClientMenu extends GuiScreen {

    private ClientFont clientFont;

    @Override
    public void initGui() {
        clientFont = new ClientFont("client/font/Nunito-Bold.ttf", 20f);

        this.buttonList.add(new FischButton(1, this.width / 2 - 100, this.height / 2 - 40, 200, 30, "Singleplayer", 8));
        this.buttonList.add(new FischButton(2, this.width / 2 - 100, this.height / 2, 200, 30, "Multiplayer", 8));
        this.buttonList.add(new FischButton(3, this.width / 2 - 100, this.height / 2 + 40, 200, 30, "Settings", 8));
        this.buttonList.add(new FischImageButton(4, width - 35, 10, 25, 25, new ResourceLocation("client/menu/exit.png"), 25, 25, 6, false));

        super.initGui();
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("client/menu/backgroundWithLogo.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, this.width, this.height);

        clientFont.drawString(FischMod.NAME, this.width / 2 - clientFont.getStringWidth(FischMod.NAME) / 2, this.height / 4, new Color(255, 255, 255).getRGB());
        final String s1 = "© Mojang AB";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 10, this.height - 10, new Color(200, 200, 200, 150).getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                this.mc.shutdown();
                break;
            }
        }
        super.actionPerformed(button);
    }
}
