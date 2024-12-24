package de.fischboy.fischmod.api.utils.visual;

import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public class RenderRoundedUtil implements IMinecraft {

    public static void drawRoundedRect(float x, float y, float w, float h, int radius, Color color) {
        RenderUtil.drawRect(x + radius, y + radius, w - radius * 2, h - radius * 2, color);
        RenderUtil.drawRect(x + radius, y, w - radius * 2, radius, color);
        RenderUtil.drawRect(x + w - radius, y + radius, radius, h - radius * 2, color);
        RenderUtil.drawRect(x + radius, y + h - radius, w - radius * 2, radius, color);
        RenderUtil.drawRect(x, y + radius, radius, h - radius * 2, color);

        drawCircle(x + radius, y + radius, radius, 180, 270, color.getRGB());
        drawCircle(x + w - radius, y + radius, radius, 270, 360, color.getRGB());
        drawCircle(x + radius, y + h - radius, radius, 90, 180, color.getRGB());
        drawCircle(x + w - radius, y + h - radius, radius, 0, 90, color.getRGB());
    }

    public static void drawRoundedRectWithShadow(float x, float y, float width, float height, int radius, Color fillColor, Color shadowColor, float shadowOffset) {
        drawRoundedRect(x + shadowOffset, y + shadowOffset, width, height, radius, shadowColor);

        drawRoundedRect(x, y, width, height, radius, fillColor);
    }

    public static void drawCircle(float x, float y, float r, int h, int j, int color) {
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        glBegin(GL_TRIANGLE_FAN);

        color(color);

        float var;
        glVertex2f(x, y);
        for (var = h; var <= j; var++) {
            color(color);
            glVertex2f(
                    (float) (r * Math.cos(Math.PI * var / 180) + x),
                    (float) (r * Math.sin(Math.PI * var / 180) + y)
            );
        }

        glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL_BLEND);
    }

    public static void drawCircleOutline(float x, float y, float radius, float thickness, Color color) {
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glDisable(GL_CULL_FACE);

        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glLineWidth(thickness);

        GL11.glBegin(GL_LINE_LOOP);
        for (int angle = 0; angle < 360; angle++) {
            double rad = Math.toRadians(angle);
            float xOffset = (float) (Math.cos(rad) * radius);
            float yOffset = (float) (Math.sin(rad) * radius);
            GL11.glVertex2f(x + xOffset, y + yOffset);
        }
        GL11.glEnd();

        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glEnable(GL_CULL_FACE);
        GL11.glDisable(GL_BLEND);
    }

    public static void color(int color) {
        float alpha = (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;
        GlStateManager.color(red, green, blue, alpha);
    }
}
