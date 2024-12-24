package de.fischboy.fischmod.management.module.impl.cheat;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.visual.RenderCheatUtil;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.Render3DEvent;
import de.fischboy.fischmod.management.event.impl.UpdateEvent;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.CheckBoxValue;
import de.fischboy.fischmod.management.module.value.impl.ColorValue;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ModInfo(name = "BedESP",  description = "ESP for Bed", category = ModuleCategory.CHEAT)
public class BedESP extends Module {

    private BlockPos[] bed = null;
    private final List<BlockPos[]> beds = new ArrayList<>();
    private long lastCheck = 0;

    public ColorValue color = new ColorValue("Color", new Color(0,204,255));
    public CheckBoxValue renderFullBlock = new CheckBoxValue("Render Full Block", false);
    public CheckBoxValue firstBed = new CheckBoxValue("First Bed", false);

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (System.currentTimeMillis() - lastCheck < 0.4 * 1000) { return; }
        lastCheck = System.currentTimeMillis();
        int i;
        priorityLoop:
        for (int n = i = 10; i >= -n; --i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    final BlockPos blockPos = new BlockPos(mc.thePlayer.posX + j, mc.thePlayer.posY + i, mc.thePlayer.posZ + k);
                    final IBlockState getBlockState = mc.theWorld.getBlockState(blockPos);
                    if (getBlockState.getBlock() == Blocks.bed && getBlockState.getValue((IProperty) BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                        if (firstBed.getValue()) {
                            if (this.bed != null && isSamePos(blockPos, this.bed[0])) {
                                return;
                            }
                            this.bed = new BlockPos[]{blockPos, blockPos.offset((EnumFacing) getBlockState.getValue((IProperty) BlockBed.FACING))};
                            return;
                        } else {
                            for (int l = 0; l < this.beds.size(); ++l) {
                                if (isSamePos(blockPos, ((BlockPos[]) this.beds.get(l))[0])) {
                                    continue priorityLoop;
                                }
                            }
                            this.beds.add(new BlockPos[]{blockPos, blockPos.offset((EnumFacing) getBlockState.getValue((IProperty) BlockBed.FACING))});
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent e) {
        if (e.entity == mc.thePlayer) {
            this.beds.clear();
            this.bed = null;
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent e){
        float blockHeight = getBlockHeight();
        if (firstBed.getValue() && this.bed != null) {
            if (!(mc.theWorld.getBlockState(bed[0]).getBlock() instanceof BlockBed)) {
                this.bed = null;
                return;
            }
            RenderCheatUtil.renderBed(this.bed, blockHeight, color.getValue().getRGB());
        }
        if (this.beds.isEmpty()) {
            return;
        }
        Iterator<BlockPos[]> iterator = this.beds.iterator();
        while (iterator.hasNext()) {
            BlockPos[] blockPos = iterator.next();
            if (!(mc.theWorld.getBlockState(blockPos[0]).getBlock() instanceof BlockBed)) {
                iterator.remove();
                continue;
            }
            RenderCheatUtil.renderBed(blockPos, blockHeight, color.getValue().getRGB());
        }
    }

    private float getBlockHeight() {
        return (renderFullBlock.getValue() ? 1 : 0.5625F);
    }

    public static boolean isSamePos(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos == blockPos2 || (blockPos.getX() == blockPos2.getX() && blockPos.getY() == blockPos2.getY() && blockPos.getZ() == blockPos2.getZ());
    }

    @Override
    public void onEnable() {
        FischMod.INSTANCE.getEventManager().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        this.bed = null;
        this.beds.clear();
        FischMod.INSTANCE.getEventManager().unregister(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
