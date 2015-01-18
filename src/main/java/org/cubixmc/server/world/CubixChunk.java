package org.cubixmc.server.world;

import org.cubixmc.util.Vector2I;
import org.cubixmc.world.Chunk;
import org.cubixmc.world.World;

public class CubixChunk implements Chunk {
    private final CubixWorld world;
    private final int x;
    private final int z;

    public CubixChunk(CubixWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public Vector2I getPosition() {
        return new Vector2I(x, z);
    }
}
