package de.fischboy.fischmod.api.utils.visual;

import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class ClientFont {

    private Font customFont;
    private FontRenderer fontRenderer;

    public ClientFont(String fontPath, float fontSize) {
        customFont = loadFont(fontPath, fontSize);
        fontRenderer = new FontRenderer(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
    }

    public void drawString(String text, int x, int y, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        fontRenderer.drawString(text, x, y, color);
        GlStateManager.popMatrix();
    }

    public int getStringWidth(String text) {
        return fontRenderer.getStringWidth(text);
    }

    private Font loadFont(String fontPath, float fontSize) {
        try (InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(fontPath)).getInputStream()) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (FontFormatException e) {
            e.printStackTrace();
            ConsoleUtil.error("Fehler beim Format der Schriftart.");
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleUtil.error("Fehler beim Laden der Schriftart. Standard-Schriftart wird verwendet.");
        }
        return new Font("Arial", Font.PLAIN, (int) fontSize);
    }
}
