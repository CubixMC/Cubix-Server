package org.cubixmc.server.world;

import org.cubixmc.inventory.Material;
import org.cubixmc.util.Vector3I;
import org.cubixmc.world.World;

public class CubixBlock {
    private final CubixWorld world;
    private final Vector3I position;
    private Material type;
    private short data;

    CubixBlock(CubixWorld world, int x, int y, int z) {
        this(world, x, y, z, world.getChunk(x >> 4, z >> 4).getType(x % 16, y, z % 16), world.getChunk(x >> 4, z >> 4).getData(x % 16, y, z % 16));
    }

    CubixBlock(CubixWorld world, int x, int y, int z, Material type, short data) {
        this.world = world;
        this.position = new Vector3I(x, y, z);
        this.type = type;
        this.data = data;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getZ() {
        return position.getZ();
    }

    public World getWorld() {
        return world;
    }

    public Vector3I getPosition() {
        return position;
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        setTypeAndData(type, (short) 0);
    }

    public short getData() {
        return data;
    }

    public void setData(short data) {
        this.data = data;
        CubixChunk chunk = world.getChunk(getX() >> 4, getZ() >> 4);
        chunk.setData(getX() % 16, getY(), getZ() % 16, data);
        chunk.queueBlockChange(position);
    }

    public void setTypeAndData(Material type, short data) {
        this.type = type;
        this.data = data;
        if(data > 15 || data < 0) {
            throw new IllegalArgumentException("Block data must be between 0 and 16!");
        }
        CubixChunk chunk = world.getChunk(getX() >> 4, getZ() >> 4);
        chunk.setTypeAndData(getX() % 16, getY(), getZ() % 16, type, data);
        chunk.queueBlockChange(position);
    }
}
