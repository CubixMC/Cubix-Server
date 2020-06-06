package org.cubixmc.server.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.entity.other.CubixDroppedItem;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.util.EmptyChunk;
import org.cubixmc.util.Vector2I;
import org.cubixmc.util.Vector3I;

import java.io.File;
import java.util.*;
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

    public List<CubixEntity> getEntitiesInArea(Location center, double radius) {
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
    public UUID getUID() {
        return worldData.getUuid();
    }

    @Override
    public long getSeed() {
        return worldData.getSeed();
    }

    public Location getSafeSpawnPoint() {
        Location location = worldData.getSpawnPosition().clone();
        while(location.getY() < 256 && getBlock(location).getType().isSolid()) {
            location.add(0, 1, 0);
        }
        return location;
    }

    public List<Entity> getCubixEntities() {
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

    public CubixBlock getBlock(Location pos) {
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
                droppedItem.spawn(new Location(this, block.getX(), block.getY(), block.getZ()).add(.5, 0, .5));
            }
        }
    }

    public void refreshChunks(CubixPlayer player) {
        Vector2I pos = new Vector2I(player.getPosition().getBlockX() >> 4, player.getPosition().getBlockZ() >> 4);
        for(int x = -4; x <= 4; x++) {
            for(int z = -4; z <= 4; z++) {
                chunkProvider.recalculateLight(pos.getX() + x, pos.getZ() + z);
            }
        }
    }

    // TODO: ALL

    @Override
    public Block getBlockAt(int x, int y, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getBlockAt(Location location) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockTypeIdAt(int x, int y, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockTypeIdAt(Location location) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(Location location) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Location location) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Block block) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void getChunkAtAsync(int x, int z, ChunkLoadCallback cb) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void getChunkAtAsync(Location location, ChunkLoadCallback cb) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void getChunkAtAsync(Block block, ChunkLoadCallback cb) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkLoaded(Chunk chunk) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk[] getLoadedChunks() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(Chunk chunk) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkInUse(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(Chunk chunk) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean save) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunkRequest(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean regenerateChunk(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean refreshChunk(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItem(Location location, org.bukkit.inventory.ItemStack item) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItemNaturally(Location location, org.bukkit.inventory.ItemStack item) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Arrow spawnArrow(Location location, Vector direction, float speed, float spread) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateTree(Location location, TreeType type) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location loc, EntityType type) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public LivingEntity spawnCreature(Location loc, EntityType type) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public LivingEntity spawnCreature(Location loc, CreatureType type) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightning(Location loc) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightningEffect(Location loc) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<org.bukkit.entity.Entity> getEntities() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T> cls) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<org.bukkit.entity.Entity> getEntitiesByClasses(Class<?>... classes) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Player> getPlayers() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<org.bukkit.entity.Entity> getNearbyEntities(Location location, double x, double y, double z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getSpawnLocation() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setSpawnLocation(int x, int y, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTime() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTime(long time) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public long getFullTime() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFullTime(long time) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasStorm() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStorm(boolean hasStorm) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeatherDuration() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWeatherDuration(int duration) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isThundering() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThundering(boolean thundering) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getThunderDuration() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThunderDuration(int duration) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Environment getEnvironment() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getPVP() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPVP(boolean pvp) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkGenerator getGenerator() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void save() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BlockPopulator> getPopulators() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, org.bukkit.Material material, byte data) throws IllegalArgumentException {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, int blockId, byte blockData) throws IllegalArgumentException {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int data) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int data, int radius) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data, int radius) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowAnimals() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowMonsters() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Biome getBiome(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(int x, int z, Biome bio) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity(int x, int z) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxHeight() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSeaLevel() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setKeepSpawnInMemory(boolean keepLoaded) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAutoSave() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAutoSave(boolean value) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Difficulty getDifficulty() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldType getWorldType() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canGenerateStructures() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerAnimalSpawns() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerMonsterSpawns() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMonsterSpawnLimit(int limit) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAnimalSpawnLimit() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAnimalSpawnLimit(int limit) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWaterAnimalSpawnLimit(int limit) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAmbientSpawnLimit() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAmbientSpawnLimit(int limit) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getGameRules() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGameRuleValue(String rule) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setGameRuleValue(String rule, String value) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGameRule(String rule) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Spigot spigot() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldBorder getWorldBorder() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        // TODO: Implement method
        throw new UnsupportedOperationException();
    }
}
