package de.fischboy.fischmod.api.utils.visual;

import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;


@UtilityClass
public class RenderUtil implements IMinecraft {

    private static final FontRenderer font = mc.fontRendererObj;

    public static void drawBorderRect(float x, float y, float width, float height, float thickness, Color color) {
        drawRect(x, y, thickness, height, color);
        drawRect(x + width, y, -thickness, height, color);
        drawRect(x, y, width, thickness, color);
        drawRect(x, y + height, width, -thickness, color);
    }

    public static void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        font.drawString(text, x - (float) font.getStringWidth(text) / 2, y, color, true);
    }

    public static void drawCenteredString(String text, float x, float y, Color color) {
        font.drawString(text, x - (float) font.getStringWidth(text) / 2, y, color.getRGB(), false);
    }

    public static void drawRect(float x, float y, float width, float height, Color color) {
        drawRectangle(x, y, x + width, y + height, color.getRGB());
    }

    public static void drawLine(float startX, float startY, float endX, float endY, float thickness, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        GL11.glLineWidth(thickness);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        worldRenderer.pos(startX, startY, 0.0D).endVertex();
        worldRenderer.pos(endX, endY, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRectangle(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }

        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawArrow(float x, float y, float width, float height, Color color, String direction) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

        GL11.glBegin(GL11.GL_TRIANGLES);
        switch (direction.toLowerCase()) {
            case "u":
            case "up":
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x - width / 2, y + height);
                GL11.glVertex2f(x + width / 2, y + height);
                break;
            case "d":
            case "down":
                GL11.glVertex2f(x, y + height);
                GL11.glVertex2f(x - width / 2, y);
                GL11.glVertex2f(x + width / 2, y);
                break;
            case "r":
            case "right":
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x - height, y - width / 2);
                GL11.glVertex2f(x - height, y + width / 2);
                break;
            case "l":
            case "left":
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x + height, y - width / 2);
                GL11.glVertex2f(x + height, y + width / 2);
            default:
                break;
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void glScissor(float x, float y, float width, float height) {
        width = (float) Math.max(width, 0.1);

        ScaledResolution sr = new ScaledResolution(mc);
        double scale = sr.getScaleFactor();

        y = sr.getScaledHeight() - y;

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
    }

    public void drawPlayer(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GuiInventory.drawEntityOnScreen(posX, posY, scale, mouseX, mouseY, ent);
    }

    public void drawPlayer(int posX, int posY, int scale, float mouseX, float mouseY) {
        GuiInventory.drawEntityOnScreen(posX, posY, scale, mouseX, mouseY, mc.thePlayer);
    }

    public static int getStringWidth(String text) {
        return font.getStringWidth(text);
    }

    public static int getStringHeight() {
        return font.FONT_HEIGHT;
    }
}
