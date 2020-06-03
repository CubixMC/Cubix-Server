package org.cubixmc.server.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.entity.other.CubixDroppedItem;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.util.EmptyChunk;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector2I;
import org.cubixmc.util.Vector3I;
import org.cubixmc.world.World;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class CubixWorld implements World {
    private final Set<CubixEntity> entities = Sets.newConcurrentHashSet();
    private final String name;
    private final Random random = new Random();
    private CubixWorldData worldData;
    private CubixChunkProvider chunkProvider;

    public CubixWorld(String name) {
        this.name = name;
    }

    public File getWorldFolder() {
        return new File(name);
    }

    /*
     * Update methods
     */

    public void tick() {
        if(chunkProvider == null) {
            return;
        }

        // Update chunk blocks
        for(final CubixChunk chunk : chunkProvider.getLoadedChunks()) {
            if(chunk == null || chunk instanceof EmptyChunk) {
                continue;
            }

            chunk.tick();
        }

        chunkProvider.update();
    }

    public void tickEntities() {
        for(CubixEntity entity : entities) {
            entity.run();
        }
    }

    //Entity Methods

    public void addEntity(CubixEntity entity) {
        entities.add(entity);
    }

    public List<CubixEntity> getEntityList() {
        return Lists.newArrayList(entities);
    }

    public List<CubixEntity> getEntitiesInArea(Position center, double radius) {
        List<CubixEntity> list = Lists.newArrayList();
        for(CubixEntity entity : entities) {
            if(entity.getPosition().distanceSquared(center) > radius * radius) continue;
            list.add(entity);
        }
        return list;
    }

    // Load & Unload methods

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

    /*
     * Implemented methods
     */

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
        List<Entity> list = Lists.newArrayListWithCapacity(entities.size());
        list.addAll(entities);
        return list;
    }

    /*
     * Chunk methods
     */

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
        Material type = block.getType();
        short data = block.getData();
        block.setType(Material.AIR);
        Material drop = type; // TODO Implement better drop system..
        if(drop != null && drop != Material.AIR) {
            int amount = 1; // TODO Implement better drop system..
            ItemStack itemStack = new ItemStack(drop, amount, drop == type ? data : 0);
            if(amount > 0) {
                CubixDroppedItem droppedItem = new CubixDroppedItem(this, itemStack, 10);
                droppedItem.spawn(new Position(this, block.getX(), block.getY(), block.getZ()).add(.5, 0, .5));
            }
        }
    }

    public void refreshChunks(CubixPlayer player) {
        Vector2I pos = player.getPosition().getChunkCoords();
        for(int x = -4; x <= 4; x++) {
            for(int z = -4; z <= 4; z++) {
                chunkProvider.recalculateLight(pos.getX() + x, pos.getZ() + z);
            }
        }
    }
}
