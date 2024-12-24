package de.fischboy.fischmod.management.event.impl;

import de.fischboy.fischmod.management.event.callables.Event;
import lombok.Getter;
import net.minecraft.block.Block;

public class RenderBlockEvent extends Event {

    @Getter
    private int x, y, z;
    @Getter
    private Block state;

    public RenderBlockEvent(int x, int y, int z, Block state) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }
}
