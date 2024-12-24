package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

@ModInfo(name = "TNT Timer", description = "TNT Timer", category = ModuleCategory.GAMEPLAY)
public class TNTTimerMod extends Module {

    public void renderTag(RenderTNTPrimed tntRenderer, EntityTNTPrimed tntPrimed, double x, double y, double z, float partialTicks) {
        if (this.isEnabled()) {
            if (tntPrimed.fuse >= 1) {
                double d0 = tntPrimed.getDistanceSqToEntity(tntRenderer.getRenderManager().livingPlayer);

                if (d0 <= 4096.0D) {
                    float number = ((float) tntPrimed.fuse - partialTicks) / 20.0F;
                    String str = (new DecimalFormat("0.00")).format((double) number);
                    FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
                    float f = 1.6F;
                    float f1 = 0.016666668F * f;

                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) x + 0.0F, (float) y + tntPrimed.height + 0.5F, (float) z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                    byte xMultiplier = 1;

                    if (mc != null && mc.gameSettings != null && mc.gameSettings.thirdPersonView == 2) {
                        xMultiplier = -1;
                    }

                    GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * (float) xMultiplier, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(-f1, -f1, f1);
                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    byte i = 0;
                    int j = fontrenderer.getStringWidth(str) / 2;
                    float green = Math.min((float) tntPrimed.fuse / 80.0F, 1.0F);
                    Color color = new Color(1.0F - green, green, 0.0F);

                    GlStateManager.enableDepth();
                    GlStateManager.depthMask(true);
                    GlStateManager.disableTexture2D();
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                    fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, color.getRGB());
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }

            }
        }
    }
}
