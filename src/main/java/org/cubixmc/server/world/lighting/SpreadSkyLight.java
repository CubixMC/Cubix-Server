package org.cubixmc.server.world.lighting;

import org.cubixmc.inventory.Material;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.packets.play.PacketOutChunkData;
import org.cubixmc.server.util.QueuedChunk;
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.Vector2I;

public class SpreadSkyLight implements Runnable {
    private final LightingManager lightingManager;
    private final CubixWorld world;
    private final Vector2I position;

    public SpreadSkyLight(LightingManager lightingManager, CubixWorld world, Vector2I position) {
        this.lightingManager = lightingManager;
        this.world = world;
        this.position = position;
    }

    @Override
    public void run() {
        CubixChunk chunk = world.getChunk(position.getX(), position.getZ());

        // Now whe spread the generated skylight
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int topY = chunk.getHeight(x, z);
                for(int y = topY; y >= 0; y--) {
                    Material type = chunk.getType(x, y, z);
                    int factor = Math.max(1, lightingManager.getOpacity(type));
                    if(factor == 15) {
                        // Looks like we are going though an obfuscated block
                        continue;
                    }
                    int light = chunk.getSkyLight(x, y, z);
                    int newLight = light + factor; // Create a new desired light with an added factor (for checking neighbors)
                    newLight = Math.max(newLight, chunk.getSkyLight(x - 1, y, z));
                    newLight = Math.max(newLight, chunk.getSkyLight(x + 1, y, z));
                    newLight = Math.max(newLight, chunk.getSkyLight(x, y, z - 1));
                    if(y > 0) {
                        newLight = Math.max(newLight, chunk.getSkyLight(x, y - 1, z));
                    }
                    if(y < 255) {
                        newLight = Math.max(newLight, chunk.getSkyLight(x, y + 1, z));
                    }
                    newLight -= factor; // Remove the factor from the light in order to restore it
                    if(newLight > light) {
                        chunk.setSkyLight(x, y, z, newLight);
                    }
                }
            }
        }

        // Once done, lets mark the chunk lighting as populated
        chunk.setLightPopulated(true);
        QueuedChunk queuedChunk = new QueuedChunk(chunk);
        queuedChunk.build();
        for(CubixPlayer player : CubixServer.getInstance().getOnlinePlayers()) {
            player.getConnection().sendPacket(new PacketOutChunkData(queuedChunk));
        }
    }
}
