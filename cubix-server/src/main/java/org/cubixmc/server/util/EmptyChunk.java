package org.cubixmc.server.util;

import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.world.ChunkSection;
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;

public class EmptyChunk extends CubixChunk {

    public EmptyChunk(CubixWorld world, int x, int z) {
        super(world, x, z);
        this.sectionCount = 1;
        this.sections[0] = new ChunkSection();
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public boolean load(CompoundTag data) throws NBTException {
        return true;
    }
}
