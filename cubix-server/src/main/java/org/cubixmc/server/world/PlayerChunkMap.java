package org.cubixmc.server.world;

import org.checkerframework.checker.units.qual.A;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.play.PacketOutChunkData;
import org.cubixmc.server.network.packets.play.PacketOutMapChunkBulk;
import org.cubixmc.server.util.EmptyChunk;
import org.cubixmc.server.util.QueuedChunk;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Vector2I;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerChunkMap {
    private static final int VIEW_DISTANCE = 10;
    private static final int SINGLE_CHUNK_LIMIT = 15;

    private final CubixPlayer player;
    private final Set<Vector2I> chunks = ConcurrentHashMap.newKeySet();

    public PlayerChunkMap(CubixPlayer player) {
        this.player = player;
    }

    /**
     * Send all near chunks to the player with as few packets as possible.
     * It checks to see if the size goes above the network limit.
     */
    public void sendAll() {
        int cx = ((int) player.getPosition().getX()) >> 4;
        int cz = ((int) player.getPosition().getZ()) >> 4;
        List<QueuedChunk> queuedChunks = new ArrayList<>();
        for(int dx = -VIEW_DISTANCE; dx <= VIEW_DISTANCE; dx++) {
            for(int dz = -VIEW_DISTANCE; dz <= VIEW_DISTANCE; dz++) {
                CubixChunk chunk = player.getWorld().getChunk(cx + dx, cz + dz);
                if(chunk == null) {
                    // Turn null chunks in to empty chunks
                    chunk = new EmptyChunk(player.getWorld(), cx + dx, cz + dz);
                }

                QueuedChunk queuedChunk = new QueuedChunk(chunk);
                queuedChunks.add(queuedChunk);
                chunks.add(chunk.getPosition());
            }
        }
        loadChunkBulk(queuedChunks);
        queuedChunks.clear();
    }

    public void movePlayer() {
        List<Vector2I> newList = new ArrayList<>();
        List<QueuedChunk> loadQueue = new ArrayList<>();

        // Remove used chunks from chunk list and load new chunks
        for(int x = -VIEW_DISTANCE; x <= VIEW_DISTANCE; x++) {
            for(int z = -VIEW_DISTANCE; z <= VIEW_DISTANCE; z++) {
                int cx = MathHelper.floor(player.getPosition().getX()) >> 4;
                int cz = MathHelper.floor(player.getPosition().getZ()) >> 4;
                Vector2I pos = new Vector2I(cx + x, cz + z);
                newList.add(pos);
                if(!chunks.remove(pos)) {
                    CubixChunk chunk = player.getWorld().getChunk(cx + x, cz + z);
                    if(chunk == null) {
                        // Turn null chunks into empty chunks
                        chunk = new EmptyChunk(player.getWorld(), cx + x, cz + z);
                    }

                    QueuedChunk queuedChunk = new QueuedChunk(chunk);
                    loadQueue.add(queuedChunk);
                }
            }
        }

        // Send the new chunks to the client
        // TODO: Sort list based on distance with player
        if(loadQueue.size() > SINGLE_CHUNK_LIMIT) {
            loadChunkBulk(loadQueue);
        } else {
            for(QueuedChunk chunk : loadQueue) {
                player.getConnection().sendPacket(new PacketOutChunkData(chunk));
            }
        }
        loadQueue.clear();

        // Unload the other chunks
        for(Vector2I pos : chunks) {
            unloadChunk(pos);
        }
        chunks.clear();
        chunks.addAll(newList);
        newList.clear();
    }

    private void loadChunkBulk(List<QueuedChunk> chunks) {
        List<QueuedChunk> queuedChunks = new ArrayList<>();
        List<PacketOutMapChunkBulk> chunkPackets = new ArrayList<>();
        int bytesLeft = NetManager.NETWORK_LIMIT - 3; // VarInt and bool should fit in 3 bytes
        for(QueuedChunk chunk : chunks) {
            bytesLeft -= chunk.size();
            if(bytesLeft < 0) {
                bytesLeft = NetManager.NETWORK_LIMIT - 3 - chunk.size(); // Reset bytes left
                chunkPackets.add(new PacketOutMapChunkBulk(true, new ArrayList<>(queuedChunks)));
                queuedChunks.clear();
            }

            queuedChunks.add(chunk);
        }
        if(!queuedChunks.isEmpty()) {
            chunkPackets.add(new PacketOutMapChunkBulk(true, new ArrayList<>(queuedChunks)));
            queuedChunks.clear();
        }
        for(PacketOutMapChunkBulk chunkPacket : chunkPackets) {
            player.getConnection().sendPacket(chunkPacket);
        }
        chunkPackets.clear();
    }

    public void unloadAll() {
        for(Vector2I pos : chunks) {
            unloadChunk(pos);
        }
        chunks.clear();
    }

    public void unloadChunk(Vector2I position) {
        if(chunks.remove(position)) {
            PacketOutChunkData packet = new PacketOutChunkData();
            packet.setChunkX(position.getX());
            packet.setChunkZ(position.getZ());
            packet.setData(new byte[0]);
            packet.setGroundUpContinuous(true);
            player.getConnection().sendPacket(packet);
        }
    }

    public boolean isChunkLoaded(CubixChunk chunk) {
        return chunks.contains(chunk.getPosition());
    }
}
