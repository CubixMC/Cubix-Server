package org.cubixmc.server.world;

import lombok.Getter;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.ListTag;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.nbt.NBTType;
import org.cubixmc.server.network.packets.play.PacketOutChunkData;
import org.cubixmc.server.util.NibbleArray;
import org.cubixmc.util.Vector2I;
import org.cubixmc.world.Chunk;
import org.cubixmc.world.World;

import java.util.Arrays;
import java.util.logging.Level;

public class CubixChunk implements Chunk {
    /**
     * Array of chunk sections.
     * 256 / 16
     *
     * Size of each is 16 ^ 3 = 4096 blocks
     */
    @Getter
    private final ChunkSection[] sections = new ChunkSection[16];
    private int sectionCount; // The amount of non-null sections

    /**
     * The heigh map for maximum block height per x/z coord.
     */
    private final int[] heightMap = new int[256];

    /**
     * Information on chunk population.
     */
    private boolean terrainPopulated;
    private boolean lightPopulated;
    private long inhabitedTime;

    private final CubixWorld world;
    private final int x;
    private final int z;

    public CubixChunk(CubixWorld world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public boolean load(CompoundTag data) throws NBTException {
        if(!data.hasWithType("Level", NBTType.COMPOUND)) {
            CubixServer.getLogger().log(Level.WARNING, toString() + " is missing level data!");
            return false;
        }

        CompoundTag level = data.getCompound("Level");
        if(!level.hasWithType("Sections", NBTType.LIST)) {
            CubixServer.getLogger().log(Level.WARNING, toString() + " is missing block sections in level data!");
            return false;
        }

        int xPos = level.getInt("xPos");
        int zPos = level.getInt("zPos");
        if(x != xPos || z != zPos) {
            // TODO: Relocate chunk
        }

        // Get the height map of the chunk
        int[] height = level.getIntArray("HeightMap");
        if(heightMap.length != height.length) {
            CubixServer.getLogger().log(Level.WARNING, toString() + " has an invalid height map size of " + height.length + "!");
            return false;
        }
        for(int i = 0; i < height.length; i++) {
            heightMap[i] = height[i];
        }

        // Get the chunk state
        this.terrainPopulated = level.getBoolean("TerrainPopulated");
        this.lightPopulated = level.getBoolean("LightPopulated");
        this.inhabitedTime = level.getLong("InHabitedTime"); // Yes, i dont even know

        // Get block data
        ListTag sections = level.getList("Sections");
        for(int i = 0; i < sections.size(); i++) {
            CompoundTag sectionTag = (CompoundTag) sections.getTag(i);
            byte[] blockIds = sectionTag.getByteArray("Blocks");
            NibbleArray blockData = new NibbleArray(sectionTag.getByteArray("Data"));
            NibbleArray add = sectionTag.hasWithType("Add", NBTType.BYTE_ARRAY) ? new NibbleArray(sectionTag.getByteArray("Add")) : null;
            char[] blocks = new char[blockIds.length];
            for(int j = 0; j < blocks.length; j++) {
                byte extra = add != null ? add.get(j) : 0; // The extra block data, idk what it is

                /*
                 * Bitmap for block:
                 * 0 -  4: Block data
                 * 4 -  8: Block id
                 * 8 - 12: Extra block data
                 */
                blocks[j] = (char) (extra << 12 | (blockIds[j]& 0xFF) << 4 | blockData.get(j));
            }

            NibbleArray blockLight = new NibbleArray(sectionTag.getByteArray("BlockLight"));
            NibbleArray skyLight = new NibbleArray(sectionTag.getByteArray("SkyLight"));
            // TODO: Only load skylight in overworld
            ChunkSection section = new ChunkSection(blocks, skyLight, blockLight);
            this.sections[i] = section;
            section.recount();
        }

        this.sectionCount = sections.size();
        return true;
    }

    public Material getType(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return Material.AIR;
        }

        return section.getType(x, y % 16, z);
    }

    public byte getData(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return (byte) 0;
        }

        return section.getData(x, y % 16, z);
    }

    public void setType(int x, int y, int z, Material type) {
        setTypeAndData(x, y, z, type, 0);
    }

    public void setData(int x, int y, int z, int data) {
        setTypeAndData(x, y, z, getType(x, y, z), data);
    }

    public void setTypeAndData(int x, int y, int z, Material type, int data) {
        ChunkSection section = getSection(y);
        section.setTypeAndData(x, y % 16, z, type, data);

        // Adjust height map
        int height = getHeight(x, z);
        if(type == Material.AIR && y >= height) {
            int newHeight = y;
            while(newHeight >= 0) {
                if(getType(x, z, newHeight--) != Material.AIR) {
                    break;
                }
            }

            heightMap[z << 4 | x] = newHeight;
        } else if(type != Material.AIR && y > height) {
            heightMap[z << 4 | x] = y;
        }
    }

    /**
     * Lighting methods.
     * Used by Lighting threads (TODO)
     */

    public byte getSkyLight(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return (byte) 0;
        }

        return section.getSkyLight(x, y % 16, z);
    }

    public byte getBlockLight(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return (byte) 0;
        }

        return section.getBlockLight(x, y % 16, z);
    }

    public void setSkyLight(int x, int y, int z, int light) {
        ChunkSection section = getSection(y);
        section.setSkyLight(x, y % 16, z, light);
    }

    public void setBlockLight(int x, int y, int z, int light) {
        ChunkSection section = getSection(y);
        section.setBlockLight(x, y % 16, z, light);
    }

    public int getHeight(int x, int z) {
        return heightMap[z << 4 | x];
    }

    private ChunkSection getSection(int y) {
        y >>= 4;// divide by 16
        if(y >= sectionCount) {
            // Missing sections
            for(int i = sectionCount; i <= y; i++) {
                // Add an empty chunk section
                sections[y] = new ChunkSection();
            }
        }

        return sections[y];
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        CubixChunk that = (CubixChunk) o;

        if(x != that.x) return false;
        if(z != that.z) return false;
        if(!world.equals(that.world)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + x;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "CubixChunk{" +
                "world=" + world +
                ", x=" + x +
                ", z=" + z +
                '}';
    }

    public PacketOutChunkData getPacket() {
        PacketOutChunkData packet = new PacketOutChunkData();
        packet.setChunkX(x);
        packet.setChunkZ(z);

        int length = (1 << sectionCount) - 1;
        byte[] buffer = new byte[arrayLength(Integer.bitCount(length), true, true)];
        int index = 0;

        // Write block id & data
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            char[] blocks = section.getBlocks();
            for(int j = 0; j < blocks.length; j++) {
                char hash = blocks[j];
                buffer[index++] = (byte) (hash & 0xFF);
                buffer[index++] = (byte) (hash >> '\b' & 0xFF);
            }
        }

        // Write block light
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            byte[] blockLightBytes = section.getBlockLight().getHandle();
            System.arraycopy(blockLightBytes, 0, buffer, index, blockLightBytes.length);
            index += blockLightBytes.length;
        }

        // Write sky light TODO: Check for overworld
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            byte[] skyLightBytes = section.getSkyLight().getHandle();
            System.arraycopy(skyLightBytes, 0, buffer, index, skyLightBytes.length);
            index += skyLightBytes.length;
        }

        // Write biome info TODO: Check if not flat map
        byte[] biomeBytes = new byte[256];
        Arrays.fill(biomeBytes, (byte) 1); // PLAINS
        System.arraycopy(biomeBytes, 0, buffer, index, biomeBytes.length);

        // Write to packet
        packet.setGroundUpContinuous(true);
        packet.setPrimaryBitMap(length & 0xFFFF);
        packet.setData(buffer);

        return packet;
    }

    private int arrayLength(int bitSize, boolean biomeInfo, boolean skyLight) {
        int sectionLength = bitSize * 2 * 16 * 16 * 16; // sections * section width ^ 3 * 2 cause block id and block data
        int blockLightLength = bitSize * 16 * 16 * 16 / 2; // sections * section width ^ 3 / 2 cause nibble is half a byte
        int skyLightLength = skyLight ? bitSize * 16 * 16 * 16 / 2 : 0; // Only if overworld
        int biomeLength = biomeInfo ? 256 : 0; // Only if not a flat map
        return sectionLength + blockLightLength + skyLightLength + biomeLength;
    }
}
