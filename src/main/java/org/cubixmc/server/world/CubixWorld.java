package org.cubixmc.server.world;

import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector3I;
import org.cubixmc.world.World;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class CubixWorld implements World {
    private final String name;
    private CubixWorldData worldData;
    private CubixChunkProvider chunkProvider;

    public CubixWorld(String name) {
        this.name = name;
    }

    public File getWorldFolder() {
        return new File(name);
    }

    public void tick() {
        // Update chunk blocks
        for(final CubixChunk chunk : chunkProvider.getLoadedChunks()) {
            chunk.tick();
        }
    }

    public boolean load() {
        getWorldFolder().mkdirs();
        File dataFile = new File(getWorldFolder(), "level.dat");
        if(dataFile.exists()) {
            try {
                this.worldData = new CubixWorldData(this);
            } catch(NBTException e) {
                CubixServer.getLogger().log(Level.WARNING, "Failed to read world data from level.dat!", e);
                return false;
            }
        } else {
            // TODO: Regenerate
        }

        this.chunkProvider = new CubixChunkProvider(this);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUUID() {
        return worldData.getUuid();
    }

    @Override
    public long getSeed() {
        return worldData.getSeed();
    }

    public Position getSpawnPosition() {
        return worldData.getSpawnPosition();
    }

    @Override
    public List<Entity> getEntities() {
        return null;
    }

    /**
     * Get chunk, load or generate if needed.
     *
     * @param x of chunk
     * @param z of chunk
     * @return Chunk instance
     */
    public CubixChunk getChunk(int x, int z) {
        return chunkProvider.getChunk(x, z, true, true);
    }

    public CubixBlock getBlock(Position pos) {
        return getBlock(new Vector3I(pos));
    }

    public CubixBlock getBlock(Vector3I pos) {
        return getBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    public CubixBlock getBlock(int x, int y, int z) {
        return new CubixBlock(this, x, y, z);
    }

    public void breakNaturally(int x, int y, int z) {
        CubixBlock block = new CubixBlock(this, x, y, z);
        block.setType(Material.AIR);
        // TODO: Drop item
    }
}
