package org.cubixmc.server.world.lighting;

import org.cubixmc.inventory.Material;
import org.cubixmc.server.threads.Threads;
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.Vector2I;

/**
 * Thread used to init height map and skylight in the overworld.
 */
public class InitSkylightTask implements Runnable {
    private final LightingManager lightingManager;
    private final CubixWorld world;
    private final Vector2I position;
    private final boolean soft;

    public InitSkylightTask(LightingManager lightingManager, CubixWorld world, Vector2I position, boolean soft) {
        this.lightingManager = lightingManager;
        this.world = world;
        this.position = position;
        this.soft = soft;
    }

    @Override
    public void run() {
        CubixChunk chunk = world.getChunk(position.getX(), position.getZ());

        // Start with the initialization of the sky light
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int topY = 0; // Top kek
                for(int y = 15; y >= 0; y++) {
                    if(chunk.sectionExists(y)) {
                        topY = y << 4 + 15;
                    }
                }

                // Now we will generate the height map & init lighting
                int height = 0;
                int light = 15; // Start at 15 and count downwards (the higher the brighter)
                for(int y = topY; y >= 0; y--) {
                    if(!chunk.sectionExists(y >> 4)) {
                        // If its not set, we skip
                        continue;
                    }

                    Material type = chunk.getType(x, y, z);
                    int opacity = lightingManager.getOpacity(type);
                    chunk.setSkyLight(x, y, z, light); // Update the light
                    light -= opacity; // Subtract the opacity from the light (solid blocks will remove 15, which is everything)
                    if(light < 15 && y > height) {
                        // Seems like we found the first non-transparent block
                        height = y;
                    }
                }

                // Now we know what the highest non-transparent block is, save it.
                chunk.setHeight(x, z, height);
            }
        }

        // Spread the block light (not populated or forced)
        if(!chunk.isLightPopulated() || !soft) {
            Threads.worldExecutor.execute(new SpreadSkyLight(lightingManager, world, position));
        }
    }
}
