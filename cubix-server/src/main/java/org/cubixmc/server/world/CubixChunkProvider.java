package org.cubixmc.server.world;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.util.EmptyChunk;
import org.cubixmc.server.world.lighting.LightingManager;
import org.cubixmc.util.Vector2I;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CubixChunkProvider {
    /**
     * The limit of chunks that can be unloaded per tick.
     * Unloading chunks locks all chunk tasks so spreading it out prevents lag spikes.
     */
    private static final int CHUNK_UNLOAD_LIMIT = 6;

    /**
     * Region file cache expiry (in minutes).
     * Defines when a region file should be removed from the cache if not used.
     */
    private final long CACHE_EXPIRY = 15L;
    private final LoadingCache<Vector2I, RegionFile> regionFileCache;

    /**
     * Concurrent chunk storage to allow multi-threading
     */
    private final Map<Vector2I, CubixChunk> chunkMap = new ConcurrentHashMap<>();
    private final Queue<Vector2I> unloadQueue = new ConcurrentLinkedQueue<>();
    private final CubixWorld world;
    private final LightingManager lightingManager;

    public CubixChunkProvider(CubixWorld world) {
        this.world = world;
        this.lightingManager = new LightingManager(world);
        File regionFile = new File(world.getWorldFolder(), "region");
        RegionFileLoader regionFileLoader = new RegionFileLoader(regionFile);
        this.regionFileCache = Caffeine.newBuilder()
                .expireAfterAccess(CACHE_EXPIRY, TimeUnit.MINUTES)
                .softValues().build(regionFileLoader);
    }

    public void update() {
        chunkMap.values().stream()
                .filter(chunk -> CubixServer.getInstance().getOnlinePlayers().stream()
                        .noneMatch(player -> player.getPlayerChunkMap().isChunkLoaded(chunk)))
                //.limit(10) // max unloads per tick
                .forEach(this::unloadChunk);

//        System.out.println(chunkMap.size());
    }

    private void unloadChunk(CubixChunk chunk) {
        // TODO: Async?
        if(!(chunk instanceof EmptyChunk)) {
            RegionFile regionFile = regionFileCache.get(new Vector2I(chunk.getX() >> 5, chunk.getZ() >> 5));
            regionFile.saveChunk(chunk.getX() & 31, chunk.getZ() & 31, chunk.saveToTag());
        }
        // one last check to see if this chunk is still unused
        if(CubixServer.getInstance().getOnlinePlayers().stream()
                .noneMatch(player -> player.getPlayerChunkMap().isChunkLoaded(chunk))) {
            chunkMap.remove(chunk.getPosition());
        }
    }

    public Collection<CubixChunk> getLoadedChunks() {
        return chunkMap.values();
    }

    public CubixChunk getChunk(int x, int z, boolean load, boolean generate) {
        Vector2I position = new Vector2I(x, z);
        CubixChunk chunk = chunkMap.get(position);
        if(chunk != null) {
            return chunk;
        } else if(!load) {
            return null;
        }

        // Load chunk from file
        Vector2I filePos = new Vector2I(x >> 5, z >> 5);
        int relX = x & 31;
        int relZ = z & 31;

        try {
            RegionFile regionFile = regionFileCache.get(filePos);
            CompoundTag chunkData = regionFile.loadChunk(relX, relZ);
            if(chunkData != null) {
                chunk = new CubixChunk(world, x, z);
                chunkMap.put(position, chunk);
                try {
                    chunk.load(chunkData);
                } catch(NBTException e) {
                    CubixServer.getLogger().log(Level.WARNING, "Corrupted chunk data for " + chunk.toString());
                    chunk = new EmptyChunk(world, x, z);
                }
            } else if(generate) {
                // TODO: Generate chunk
            } else {
                chunk = new EmptyChunk(world, x, z);
            }
        } catch(Exception e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to load region file", e);
        }

        return chunk;
    }

    public boolean recalculateLight(int x, int z) {
        CubixChunk chunk = getChunk(x, z, true, false);
        if(chunk == null) {
            return false;
        }

        chunk.clearSkyLight();
        chunk.clearBlockLight();
        lightingManager.initLight(chunk, false);
        return true;
    }

    private record RegionFileLoader(File regionFile) implements CacheLoader<Vector2I, RegionFile> {

        @Override
            public RegionFile load(Vector2I position) throws Exception {
                String name = "r." + position.getX() + "." + position.getZ() + ".mca";
                File file = new File(regionFile, name);
                return new RegionFile(file);
            }
        }
}
