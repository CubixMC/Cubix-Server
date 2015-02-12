package org.cubixmc.server.world;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.util.EmptyChunk;
import org.cubixmc.util.Vector2I;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
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
    private final Map<Vector2I, CubixChunk> chunkMap = Maps.newConcurrentMap();
    private final Queue<Vector2I> unloadQueue = Queues.newConcurrentLinkedQueue();
    private final CubixWorld world;

    public CubixChunkProvider(CubixWorld world) {
        this.world = world;
        File regionFile = new File(world.getWorldFolder(), "region");
        RegionFileLoader regionFileLoader = new RegionFileLoader(regionFile);
        this.regionFileCache = CacheBuilder.newBuilder()
                .expireAfterAccess(CACHE_EXPIRY, TimeUnit.MINUTES)
                .softValues().build(regionFileLoader);
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
        int relX = rel(x);
        int relZ = rel(z);

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
        } catch(ExecutionException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to load region file", e);
        }

        return chunk;
    }

    private int rel(int coord) {
        if(coord < 0) {
            return 32 + (coord % 32);
        } else {
            return coord % 32;
        }
    }

    private static class RegionFileLoader extends CacheLoader<Vector2I, RegionFile> {
        private final File regionFile;

        public RegionFileLoader(File regionFile) {
            this.regionFile = regionFile;
        }

        @Override
        public RegionFile load(Vector2I position) throws Exception {
            String name = "r." + position.getX() + "." + position.getZ() + ".mca";
            File file = new File(regionFile, name);
            return new RegionFile(file);
        }
    }
}
