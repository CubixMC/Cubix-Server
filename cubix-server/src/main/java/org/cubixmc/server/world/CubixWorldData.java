package org.cubixmc.server.world;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.GameMode;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.NBTException;
import org.cubixmc.server.nbt.NBTStorage;
import org.cubixmc.server.nbt.NBTType;

import java.io.*;
import java.util.UUID;
import java.util.logging.Level;

@Data
public class CubixWorldData {
    private final UUID uuid;
    private final long seed;
    private GameMode gameType;
    private Location spawnPosition;
    private long time;
    private long lastPlayed;
    private long sizeOnDisk;
    private String levelName;
    private int version;
    private int clearWeatherTime;
    private int rainTime;
    private boolean raining;
    private int thunderTime;
    private boolean thundering;
    private boolean hardcore;

    public CubixWorldData(CubixWorld world) throws NBTException {
        this(world, NBTStorage.readCompound(new File(world.getWorldFolder(), "level.dat")));
    }

    public CubixWorldData(CubixWorld world, CompoundTag compound) throws NBTException {
        if(compound.hasWithType("Data", NBTType.COMPOUND)) {
            compound = compound.getCompound("Data");
        } else {
            CubixServer.getLogger().log(Level.WARNING, "Reading world date from root node...");
        }

        this.seed = compound.getLong("RandomSeed");
        if(compound.hasWithType("generatorName", NBTType.STRING)) {
            // TODO: Set world type
        }
        this.gameType = GameMode.getById(compound.getInt("GameType"));
        int spawnX = compound.getInt("SpawnX");
        int spawnY = compound.getInt("SpawnY");
        int spawnZ = compound.getInt("SpawnZ");
        this.spawnPosition = new Location(world, spawnX, spawnY, spawnZ);
        this.time = compound.getLong("Time");
        this.lastPlayed = compound.getLong("LastPlayed");
        this.sizeOnDisk = compound.getLong("SizeOnDisc");
        this.levelName = compound.getString("LevelName");
        this.version = compound.getInt("version");
        this.clearWeatherTime = compound.getInt("clearWeatherTime");
        this.rainTime = compound.getInt("rainTime");
        this.raining = compound.getBoolean("raining");
        this.thunderTime = compound.getInt("thunderTime");
        this.thundering = compound.getBoolean("thundering");
        this.hardcore = compound.getBoolean("hardcore");

        File file = new File(world.getWorldFolder(), "uuid.dat");
        if(file.exists()) {
            UUID uuid = UUID.randomUUID();
            try {
                DataInputStream input = new DataInputStream(new FileInputStream(file));
                uuid = new UUID(input.readLong(), input.readLong());
                input.close();
            } catch(Exception e) {
                CubixServer.getLogger().log(Level.WARNING, "Failed to read world UUID from file", e);
            }

            // Fix for final field
            this.uuid = uuid;
        } else {
            this.uuid = UUID.randomUUID();
            try {
                DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
                output.writeLong(uuid.getMostSignificantBits());
                output.writeLong(uuid.getLeastSignificantBits());
                output.close();
            } catch(Exception e) {
                CubixServer.getLogger().log(Level.WARNING, "Failed to save new UUID to file", e);
            }
        }
    }

    public void save(File worldFolder) {
    }
}
