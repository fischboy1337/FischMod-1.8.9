package de.fischboy.fischmod.management.module.impl.cheat;

import de.fischboy.fischmod.api.utils.Utils;
import de.fischboy.fischmod.api.utils.visual.RenderCheatUtil;
import de.fischboy.fischmod.api.utils.visual.RenderRoundedUtil;
import de.fischboy.fischmod.api.utils.visual.RenderUtil;
import de.fischboy.fischmod.management.module.HudModule;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.Bounds;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Bounds(posX = 20, posY = 20, width = 110, height = 50)
@ModInfo(name = "Murder Finder", description = "Finds the Murder", category = ModuleCategory.CHEAT)
public class MurderMysteryMod extends HudModule {

    private final List<EntityPlayer> murderers = new ArrayList();
    private final List<EntityPlayer> hasBow = new ArrayList();
    private boolean override;
    private ArrayList<Item> items = new ArrayList<Item>();

    public CheckBoxValue espInno = new CheckBoxValue("ESP Innocent", true);
    public CheckBoxValue espDetective = new CheckBoxValue("ESP Detective", true);
    public CheckBoxValue espMurderer = new CheckBoxValue("ESP Murder", true);
    public CheckBoxValue hud = new CheckBoxValue("HUD", true);
    public CheckBoxValue goldESP = new CheckBoxValue("Gold ESP", false);

    public MurderMysteryMod() {
        items.add(new Item().getByNameOrId("minecraft:iron_sword"));
        items.add(new Item().getByNameOrId("minecraft:stone_sword"));
        items.add(new Item().getByNameOrId("minecraft:iron_shovel"));
        items.add(new Item().getByNameOrId("minecraft:stick"));
        items.add(new Item().getByNameOrId("minecraft:wooden_axe"));
        items.add(new Item().getByNameOrId("minecraft:wooden_sword"));
        items.add(new Item().getByNameOrId("minecraft:deadbush"));
        items.add(new Item().getByNameOrId("minecraft:reeds"));
        items.add(new Item().getByNameOrId("minecraft:stone_shovel"));
        items.add(new Item().getByNameOrId("minecraft:blaze_rod"));
        items.add(new Item().getByNameOrId("minecraft:diamond_shovel"));
        items.add(new Item().getByNameOrId("minecraft:quartz"));
        items.add(new Item().getByNameOrId("minecraft:pumpkin_pie"));
        items.add(new Item().getByNameOrId("minecraft:golden_pickaxe"));
        items.add(new Item().getByNameOrId("minecraft:leather"));
        items.add(new Item().getByNameOrId("minecraft:name_tag"));
        items.add(new Item().getByNameOrId("minecraft:coal"));
        items.add(new Item().getByNameOrId("minecraft:flint"));
        items.add(new Item().getByNameOrId("minecraft:bone"));
        items.add(new Item().getByNameOrId("minecraft:carrot"));
        items.add(new Item().getByNameOrId("minecraft:golden_carrot"));
        items.add(new Item().getByNameOrId("minecraft:cookie"));
        items.add(new Item().getByNameOrId("minecraft:diamond_axe"));
        items.add(new Item().getByNameOrId("minecraft:double_plant"));
        items.add(new Item().getByNameOrId("minecraft:prismarine_shard"));
        items.add(new Item().getByNameOrId("minecraft:cooked_beef"));
        items.add(new Item().getByNameOrId("minecraft:netherbrick"));
        items.add(new Item().getByNameOrId("minecraft:cooked_chicken"));
        items.add(new Item().getByNameOrId("minecraft:record_blocks"));
        items.add(new Item().getByNameOrId("minecraft:golden_hoe"));
        items.add(new Item().getByNameOrId("minecraft:dye"));
        items.add(new Item().getByNameOrId("minecraft:golden_sword"));
        items.add(new Item().getByNameOrId("minecraft:diamond_sword"));
        items.add(new Item().getByNameOrId("minecraft:diamond_hoe"));
        items.add(new Item().getByNameOrId("minecraft:shears"));
        items.add(new Item().getByNameOrId("minecraft:fish"));
        items.add(new Item().getByNameOrId("minecraft:bread"));
        items.add(new Item().getByNameOrId("minecraft:boat"));
        items.add(new Item().getByNameOrId("minecraft:speckled_melon"));
        items.add(new Item().getByNameOrId("minecraft:book"));
        items.add(new Item().getByNameOrId("minecraft:sapling"));
        items.add(new Item().getByNameOrId("minecraft:golden_axe"));
        items.add(new Item().getByNameOrId("minecraft:diamond_pickaxe"));
        items.add(new Item().getByNameOrId("minecraft:golden_shovel"));

    }

    @Override
    public void render() {
        if (!this.isMurderMystery()) {
            this.clear();
        } else {
            RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
            float centerX = this.getPosX() + this.getBounds().width() / 2;
            float posY = this.getPosY() + 10;

            String titleText = "Murderers Detected:";
            RenderUtil.drawCenteredString(titleText, centerX, posY, Color.YELLOW);
            posY += mc.fontRendererObj.FONT_HEIGHT + 5;

            if (!murderers.isEmpty()) {
                for (EntityPlayer murderer : murderers) {
                    String murdererText = EnumChatFormatting.AQUA + murderer.getName() + EnumChatFormatting.GRAY + " (" + EnumChatFormatting.LIGHT_PURPLE + (int) mc.thePlayer.getDistanceToEntity(murderer) + "m" + EnumChatFormatting.GRAY + ")";
                    RenderUtil.drawCenteredString(murdererText, centerX, posY, Color.WHITE);
                    posY += mc.fontRendererObj.FONT_HEIGHT + 2;
                }
            } else {
                String noMurderText = "No Murder detected";
                RenderUtil.drawCenteredString(noMurderText, centerX, posY, Color.WHITE);
            }
        }
    }

    @Override
    public void renderDummy() {
        RenderRoundedUtil.drawRoundedRect(this.getPosX(), this.getPosY(), this.getBounds().width(), this.getBounds().height(), getRadius().getValue().intValue(), getBackground().getValue());
        float centerX = this.getPosX() + this.getBounds().width() / 2;
        float posY = this.getPosY() + 10;

        String titleText = EnumChatFormatting.YELLOW + "Murderers Detected:";
        RenderUtil.drawCenteredString(titleText, centerX, posY, Color.WHITE);
        posY += mc.fontRendererObj.FONT_HEIGHT + 5;

        String[] dummyMurderers = {"_fairpxlt3_", "fischboy1337"};
        for (String murdererName : dummyMurderers) {
            String murdererText = EnumChatFormatting.AQUA + murdererName + EnumChatFormatting.GRAY + " (10m)";
            RenderUtil.drawCenteredString(murdererText, centerX, posY, Color.WHITE);
            posY += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }

    @SubscribeEvent
    public void onRenderWordLast(RenderWorldLastEvent e) {
        if (!this.isMurderMystery()) {
            this.clear();
        } else {
            override = false;
            for (EntityPlayer en : mc.theWorld.playerEntities) {
                if (en != mc.thePlayer && !en.isInvisible()) {
                    if (en.getHeldItem() != null && en.getHeldItem().hasDisplayName()) {
                        Item heldItem = en.getHeldItem().getItem();
                        if (items.contains(heldItem)) {
                            if (!murderers.contains(en)) {
                                murderers.add(en);
                                if (hud.getValue()) {
                                    mc.thePlayer.playSound("note.pling", 1.0F, 1.0F);
                                    this.render();
                                }
                            }
                        } else if (heldItem instanceof ItemBow && espDetective.getValue() && !hasBow.contains(en)) {
                            hasBow.add(en);
                        }
                    }
                    override = true;
                    int rgb = Color.green.getRGB();
                    if (murderers.contains(en) && espMurderer.getValue()) {
                        rgb = Color.red.getRGB();
                    } else if (hasBow.contains(en) && espDetective.getValue()) {
                        rgb = Color.orange.getRGB();
                    }
                    if (rgb == Color.GREEN.getRGB() && espInno.getValue()) {
                        RenderCheatUtil.renderEntity(en, 2, 0.0D, 0.0D, rgb, false);
                    } else if (rgb == Color.RED.getRGB() || rgb == Color.ORANGE.getRGB()) {
                        RenderCheatUtil.renderEntity(en, 2, 0.0D, 0.0D, rgb, false);
                    }
                }
            }

            if (!goldESP.getValue()) {
                return;
            }
            float renderPartialTicks = Utils.getTimer().renderPartialTicks;
            int n4 = -331703;
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    if (entity.ticksExisted < 3) {
                        continue;
                    }
                    EntityItem entityItem = (EntityItem) entity;
                    if (entityItem.getEntityItem().stackSize == 0) {
                        continue;
                    }
                    Item getItem = entityItem.getEntityItem().getItem();
                    if (getItem == null) {
                        continue;
                    }
                    double n5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks;
                    double n6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks;
                    double n7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks;
                    double n8 = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * renderPartialTicks - n5;
                    double n9 = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * renderPartialTicks - n6;
                    double n10 = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * renderPartialTicks - n7;
                    GlStateManager.pushMatrix();
                    drawBox(n4, n5, n6, n7, MathHelper.sqrt_double(n8 * n8 + n9 * n9 + n10 * n10));
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public void drawBox(int n, double n4, double n5, double n6, double n7) {
        n4 -= mc.getRenderManager().viewerPosX;
        n5 -= mc.getRenderManager().viewerPosY;
        n6 -= mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        float n8 = (n >> 16 & 0xFF) / 255.0f;
        float n9 = (n >> 8 & 0xFF) / 255.0f;
        float n10 = (n & 0xFF) / 255.0f;
        float min = Math.min(Math.max(0.2f, (float) (0.009999999776482582 * n7)), 0.4f);
        RenderCheatUtil.drawBoundingBox(new AxisAlignedBB(n4 - min, n5, n6 - min, n4 + min, n5 + min * 2.0f, n6 + min), n8, n9, n10, 0.35f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private boolean isMurderMystery() {
        if (Utils.isHypixel()) {
            if (mc.thePlayer.getWorldScoreboard() == null || mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
                return false;
            }

            String d = mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            if (!d.contains("MURDER") && !d.contains("MYSTERY")) {
                return false;
            }

            Iterator var2 = Utils.gsl().iterator();

            while (var2.hasNext()) {
                String l = (String) var2.next();
                String s = Utils.stripColor(l);
                if (s.contains("Role:") || s.contains("Innocents Left:")) {
                    return true;
                }
            }
        }

        return false;
    }

    private void clear() {
        override = false;
        murderers.clear();
        hasBow.clear();
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        this.clear();
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
