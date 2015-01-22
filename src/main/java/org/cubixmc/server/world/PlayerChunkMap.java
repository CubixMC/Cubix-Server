package org.cubixmc.server.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.play.PacketOutChunkData;
import org.cubixmc.server.network.packets.play.PacketOutMapChunkBulk;
import org.cubixmc.server.util.QueuedChunk;
import org.cubixmc.util.Vector2I;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerChunkMap {
    private final CubixPlayer player;
    private final Set<Vector2I> chunks = Sets.newConcurrentHashSet();

    public PlayerChunkMap(CubixPlayer player) {
        this.player = player;
    }

    /**
     * Send all near chunks to the player with as few packets as possible.
     * It checks to see if the size goes above the network limit.
     *
     * @param radius of chunks to load
     */
    public void sendAll(int radius) {
        int cx = ((int) player.getPosition().getX()) >> 4;
        int cz = ((int) player.getPosition().getZ()) >> 4;
        List<QueuedChunk> queuedChunks = Lists.newArrayList();
        List<PacketOutMapChunkBulk> chunkPackets = Lists.newArrayList();
        int bytesLeft = NetManager.NETWORK_LIMIT - 3; // VarInt and bool should fit in 3 bytes
        for(int dx = -radius; dx <= radius; dx++) {
            for(int dz = -radius; dz <= radius; dz++) {
                CubixChunk chunk = player.getWorld().getChunk(cx + dx, cz + dz);
                if(chunk != null) {
                    QueuedChunk queuedChunk = new QueuedChunk(chunk);
                    queuedChunk.build();
                    bytesLeft -= queuedChunk.size();
                    if(bytesLeft < 0) {
                        bytesLeft = NetManager.NETWORK_LIMIT - 3 - queuedChunk.size(); // Reset
                        chunkPackets.add(new PacketOutMapChunkBulk(true, new ArrayList<>(queuedChunks)));
                        queuedChunks.clear();
                    }

                    queuedChunks.add(queuedChunk);
                }
            }
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

    public void unloadChunk(Vector2I position) {
        if(chunks.remove(position)) {
            PacketOutChunkData packet = new PacketOutChunkData();
            packet.setChunkX(position.getX());
            packet.setChunkZ(position.getZ());
            player.getConnection().sendPacket(packet);
        }
    }
}
