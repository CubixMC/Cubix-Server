package org.cubixmc.server.world;

import org.cubixmc.entity.Entity;
import org.cubixmc.server.CubixServer;
import org.cubixmc.util.Vector2I;
import org.cubixmc.world.World;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class CubixWorld implements World {
    private final Map<Vector2I, CubixChunk> chunkMap = new ConcurrentHashMap<>();
    private final String name;
    private UUID uuid;

    public CubixWorld(String name) {
        this.name = name;
    }

    public void load() {
        File dir = new File(name);

        // Read UUID
        File uuidFile = new File(dir, "uuid.dat");
        if(uuidFile.exists()) {
            try {
                DataInputStream input = new DataInputStream(new FileInputStream(uuidFile));
                this.uuid = new UUID(input.readLong(), input.readLong());
                input.close();
            } catch(IOException e) {
                CubixServer.getLogger().log(Level.WARNING, "Failed to load UUID from world file", e);
            }
        } else {
            this.uuid = UUID.randomUUID();
            try {
                DataOutputStream output = new DataOutputStream(new FileOutputStream(uuidFile));
                output.writeLong(uuid.getMostSignificantBits());
                output.writeLong(uuid.getLeastSignificantBits());
                output.flush();
                output.close();
            } catch(IOException e) {
                CubixServer.getLogger().log(Level.WARNING, "Failed to save UUID to world file", e);
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public long getSeed() {
        return 0;
    }

    @Override
    public List<Entity> getEntities() {
        return null;
    }
}
