package org.cubixmc.server.world;

import org.cubixmc.entity.Entity;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.util.Vector2I;
import org.cubixmc.world.World;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class CubixWorld implements World {
    private final String name;
    private CubixWorldData worldData;

    public CubixWorld(String name) {
        this.name = name;
    }

    public File getWorldFolder() {
        return new File(name);
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

    @Override
    public List<Entity> getEntities() {
        return null;
    }
}
