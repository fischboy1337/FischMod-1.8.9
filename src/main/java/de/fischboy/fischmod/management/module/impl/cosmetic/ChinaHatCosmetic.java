package de.fischboy.fischmod.management.module.impl.cosmetic;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.Utils;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render3DEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;

@ModInfo(name = "China Hat", description = "Gives you a China hat", category = ModuleCategory.GAMEPLAY)
public class ChinaHatCosmetic extends Module {

    public CheckBoxValue renderInFirstPerson = new CheckBoxValue("Show In First Person", false);
    public SliderValue side = new SliderValue("Side", 30, 50, 45, 1);
    public SliderValue stack = new SliderValue("Stack", 45, 200, 50, 1);
    public ColorValue hatColor = new ColorValue("Color", new Color(-1));

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (mc.gameSettings.thirdPersonView == 0 && !renderInFirstPerson.getValue()) {
            return;
        }
        this.drawChinaHat(mc.thePlayer);
    }

    private void drawChinaHat(EntityLivingBase entity) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) Utils.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) Utils.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) Utils.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
        int side = this.side.getValue().intValue();
        int stack = this.stack.getValue().intValue();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + (mc.thePlayer.isSneaking() ? 2.0 : 2.2), z);
        GL11.glRotatef(-entity.width, 0.0f, 1.0f, 0.0f);

        Color col = hatColor.getValue();
        GL11.glColor4f(col.getRed() / 255f, col.getGreen() / 255f, col.getBlue() / 255f, 0.3f);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(1.0f);

        Cylinder c = new Cylinder();
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.0f, 0.8f, 0.4f, side, stack);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();
    }

    @Override
    public void onEnable() {
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        FischMod.INSTANCE.getEventManager().unregister(this);
    }
}
