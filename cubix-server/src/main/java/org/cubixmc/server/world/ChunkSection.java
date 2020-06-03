package org.cubixmc.server.world;

import lombok.Getter;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.util.NibbleArray;

public class ChunkSection {
    /**
     * An array of all block ids and data
     */
    @Getter
    private char[] blocks;
    /**
     * The light obtained from the sky as a nibble (0-15)
     */
    @Getter
    private NibbleArray skyLight;
    /**
     * The light obtained from blocks as a nibble (0-15)
     */
    @Getter
    private NibbleArray blockLight;
    /**
     * The amount of NON air blocks in this section
     */
    private int count;

    public ChunkSection() {
        this.blocks = new char[4096];
        this.skyLight = new NibbleArray(4096);
        this.blockLight = new NibbleArray(4096);
        skyLight.fill((byte) 0xf); // 0xf = 15 = maximum sky light, obviously.
    }

    public ChunkSection(char[] blocks, NibbleArray skylight, NibbleArray blockLight) {
        this.blocks = blocks;
        this.skyLight = skylight;
        this.blockLight = blockLight;
    }

    public void recount() {
        this.count = 0;
        for(char id : blocks) {
            if(id != 0) {
                count += 1;
            }
        }
    }

    public Material getType(int x, int y, int z) {
        return Material.getMaterial((block(x, y, z) >> 4) & 0xFF);
    }

    public byte getData(int x, int y, int z) {
        return (byte) (block(x, y, z) & 0xF);
    }

    public byte getExtraData(int x, int y, int z) {
        return (byte) (block(x, y, z) >> 12);
    }

    public void setType(int x, int y, int z, Material type) {
        setTypeAndData(x, y, z, type, getData(x, y, z));
    }

    public void setData(int x, int y, int z, int data) {
        setTypeAndData(x, y, z, getType(x, y, z), data);
    }

    public void setTypeAndData(int x, int y, int z, Material type, int data) {
        int hash = y << 8 | z << 4 | x;
        int extraData = getExtraData(x, y, z);
        char value = (char) ((extraData & 0xF) << 12 | (type.getId() & 0xFF) << 4 | data & 0xF);
        blocks[hash] = value;
    }

    public byte getSkyLight(int x, int y, int z) {
        return skyLight.get(x, y, z);
    }

    public byte getBlockLight(int x, int y, int z) {
        return blockLight.get(x, y, z);
    }

    public void setSkyLight(int x, int y, int z, int value) {
        skyLight.set(x, y, z, value);
    }

    public void setBlockLight(int x, int y, int z, int value) {
        blockLight.set(x, y, z, value);
    }

    public void clearSkyLight() {
        skyLight.fill((byte) 0);
    }

    public void clearBlockLight() {
        blockLight.fill((byte) 0);
    }

    private int block(int x, int y, int z) {
        int hash = y << 8 | z << 4 | x;
        return blocks[hash];
    }


}
