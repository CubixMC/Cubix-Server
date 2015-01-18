package org.cubixmc.server.entity;

import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.Position;

import java.util.Random;
import java.util.UUID;

public abstract class CubixEntity implements org.cubixmc.entity.Entity {
    private static int ENTITY_ID = 0;

    protected final Random random = new Random();
    protected final int entityId;
    protected final Metadata metadata;
    private final UUID uuid;
    protected Position position;

    protected CubixEntity(CubixWorld world) {
        this.entityId = ENTITY_ID++;
        this.uuid = UUID.randomUUID();
        this.metadata = new Metadata();
    }

    public boolean isSpawned() {
        return position != null;
    }

    public boolean spawn(Position position) {
        if(this.position != null) {
            return false;
        }

        this.position = position;
        metadata.set(0, (byte) 0);
        return true;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean teleport(Position position) {
        return teleport(position, "unknown");
    }

    @Override
    public boolean teleport(Position to, String cause) {
        if(!position.getWorld().equals(to.getWorld())) {
            return false;
        }

        position.setX(to.getX());
        position.setY(to.getY());
        position.setZ(to.getZ());
        position.setYaw(to.getYaw());
        position.setPitch(to.getPitch());
        return true;
    }

    @Override
    public boolean teleport(org.cubixmc.entity.Entity target) {
        return teleport(target.getPosition());
    }

    @Override
    public boolean teleport(org.cubixmc.entity.Entity target, String cause) {
        return teleport(target.getPosition(), cause);
    }

    public abstract PacketOut getSpawnPacket();
}
