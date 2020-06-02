package org.cubixmc.server.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import lombok.Getter;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.*;
import org.cubixmc.server.network.packets.play.PacketOutBlockChange;
import org.cubixmc.server.network.packets.play.PacketOutChunkData;
import org.cubixmc.server.network.packets.play.PacketOutMultiBlockChange;
import org.cubixmc.server.threads.Threads;
import org.cubixmc.server.util.NibbleArray;
import org.cubixmc.server.util.QueuedChunk;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector2I;
import org.cubixmc.util.Vector3I;
import org.cubixmc.world.Chunk;
import org.cubixmc.world.World;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

public class CubixChunk implements Chunk {
    /**
     * Array of chunk sections.
     * 256 / 16
     *
     * Size of each is 16 ^ 3 = 4096 blocks
     */
    @Getter
    protected final ChunkSection[] sections = new ChunkSection[16];
    @Getter
    protected int sectionCount; // The amount of non-null sections

    /**
     * The heigh map for maximum block height per x/z coord.
     */
    private final int[] heightMap = new int[256];
    /**
     * Block change queue
     */
    private LinkedBlockingDeque<Vector3I> queuedBlockChanges = Queues.newLinkedBlockingDeque(64);

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

    public void queueBlockChange(Vector3I pos) {
        queuedBlockChanges.offer(pos);
    }

    public void tick() {
        if(queuedBlockChanges.size() > 0) {
            // For same reason single block change doesn' t work
            if(queuedBlockChanges.size() == 0) {
                Vector3I pos = queuedBlockChanges.poll();
                Material type = getType(pos.getX(), pos.getY(), pos.getZ());
                byte data = getData(pos.getX(), pos.getY(), pos.getZ());
                int blockId = type.getId() << 4 | data;
                PacketOutBlockChange packet = new PacketOutBlockChange(new Position(world, pos.getX(), pos.getY(), pos.getZ()), blockId);
                CubixServer.broadcast(packet, world, null);
            } else if(queuedBlockChanges.size() < 64) {
                // Send multi block change packet
                List<int[]> blocks = Lists.newArrayList();
                while(queuedBlockChanges.size() > 0) {
                    Vector3I pos = queuedBlockChanges.poll();
                    int x = rel(pos.getX());
                    int z = rel(pos.getZ());
                    int[] data = new int[3];
                    data[0] = x << 4 & 0xFF | z & 0xF;
                    data[1] = pos.getY();
                    data[2] = getType(x, pos.getY(), z).getId() << 4 | getData(x, pos.getY(), z);
                    blocks.add(data);
                }
                PacketOutMultiBlockChange packet = new PacketOutMultiBlockChange(getPosition(), blocks);
                CubixServer.broadcast(packet, world, null);
            } else {
                // Resend chunk
                final QueuedChunk queuedChunk = new QueuedChunk(this);
                Threads.worldExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        queuedChunk.build();
                        CubixServer.broadcast(new PacketOutChunkData(queuedChunk), world, null);
                    }
                });
                queuedBlockChanges.clear();
            }
        }
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

    public CompoundTag saveToTag() {
        CompoundTag tag = new CompoundTag();

        CompoundTag level = new CompoundTag();
        level.addTag("xPos", new IntTag(x));
        level.addTag("zPos", new IntTag(z));
        level.addTag("HeightMap", new IntArrayTag(heightMap));
        level.addTag("TerrainPopulated", ByteTag.ofBoolean(terrainPopulated));
        level.addTag("LightPopulated", ByteTag.ofBoolean(lightPopulated));
        level.addTag("InHabitedTime", new LongTag(inhabitedTime));

        ListTag sectionsTag = new ListTag(NBTType.COMPOUND);
        for(int i = 0; i < sections.length; i++) {
            ChunkSection section = sections[i];
            if(section == null) break;
            CompoundTag sectionTag = new CompoundTag();
            sectionTag.addTag("Data", blocksToNibbles(section.getBlocks(), 0));
            sectionTag.addTag("Blocks", blocksToBytes(section.getBlocks(), 4));
            sectionTag.addTag("Add", blocksToNibbles(section.getBlocks(), 12));
            sectionTag.addTag("BlockLight", new ByteArrayTag(section.getBlockLight().getHandle()));
            sectionTag.addTag("SkyLight", new ByteArrayTag(section.getSkyLight().getHandle()));
            sectionsTag.addTag(sectionTag);
        }
        level.addTag("Sections", sectionsTag);
        tag.addTag("Level", level);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        DataOutputStream daos = new DataOutputStream(baos);
//        NBTStorage.writeCompound(tag, daos);
//        System.out.println(baos.toByteArray().length);
//        NBTStorage.readCompound(new DataInputStream(new ByteArrayInputStream(baos.toByteArray())));

        return tag;
    }

    private ByteArrayTag blocksToBytes(char[] blocks, int shift) {
        byte[] bytes = new byte[blocks.length];
        for(int i = 0; i < blocks.length; i++) {
            bytes[i] = (byte) ((blocks[i] >> shift) & 0xff);
        }
        return new ByteArrayTag(bytes);
    }

    private ByteArrayTag blocksToNibbles(char[] blocks, int shift) {
        NibbleArray nibbleArray = new NibbleArray(blocks.length);
        for(int i = 0; i < blocks.length; i++) {
            nibbleArray.set(i, (blocks[i] >> shift) & 0xf);
        }
        return new ByteArrayTag(nibbleArray.getHandle());
    }

    public Material getType(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return Material.AIR;
        }

        return section.getType(rel(x), y % 16, rel(z));
    }

    public byte getData(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return (byte) 0;
        }

        return section.getData(rel(x), y % 16, rel(z));
    }

    public void setType(int x, int y, int z, Material type) {
        setTypeAndData(x, y, z, type, 0);
    }

    public void setData(int x, int y, int z, int data) {
        setTypeAndData(x, y, z, getType(x, y, z), data);
    }

    public void setTypeAndData(int x, int y, int z, Material type, int data) {
        x = rel(x);
        z = rel(z);
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

        return section.getSkyLight(rel(x), y % 16, rel(z));
    }

    public byte getBlockLight(int x, int y, int z) {
        ChunkSection section = sections[y >> 4];
        if(section == null) {
            return (byte) 0;
        }

        return section.getBlockLight(rel(x), y % 16, rel(z));
    }

    public void setSkyLight(int x, int y, int z, int light) {
        ChunkSection section = getSection(y);
        section.setSkyLight(rel(x), y % 16, rel(z), light);
    }

    public void setBlockLight(int x, int y, int z, int light) {
        ChunkSection section = getSection(y);
        section.setBlockLight(rel(x), y % 16, rel(z), light);
    }

    public void clearSkyLight() {
        for(ChunkSection section : sections) {
            if(section != null) {
                section.clearSkyLight();
            }
        }
    }

    public void clearBlockLight() {
        for(ChunkSection section : sections) {
            if(section != null) {
                section.clearBlockLight();
            }
        }
    }

    public int getHeight(int x, int z) {
        try {
            return heightMap[z << 4 | x];
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(x + ", " + z);
            throw e;
        }
    }

    public void setHeight(int x, int z, int height) {
        heightMap[z << 4 | x] = height;
    }

    public boolean sectionExists(int y) {
        return y < 16 && sections[y] != null;
    }

    public ChunkSection getSection(int y) {
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

    private int rel(int value) {
        if(value < 0) {
            return 16 + value % 16;
        } else {
            return value % 16;
        }
    }

    public boolean isTerrainPopulated() {
        return terrainPopulated;
    }

    public void setTerrainPopulated(boolean terrainPopulated) {
        this.terrainPopulated = terrainPopulated;
    }

    public boolean isLightPopulated() {
        return lightPopulated;
    }

    public void setLightPopulated(boolean lightPopulated) {
        this.lightPopulated = lightPopulated;
    }

    public long getInhabitedTime() {
        return inhabitedTime;
    }

    public void setInhabitedTime(long inhabitedTime) {
        this.inhabitedTime = inhabitedTime;
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
}
