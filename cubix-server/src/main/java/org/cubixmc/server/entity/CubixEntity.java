package org.cubixmc.server.entity;

import org.cubixmc.entity.Entity;
import org.cubixmc.entity.Player;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutEntityHeadLook;
import org.cubixmc.server.network.packets.play.PacketOutEntityLook;
import org.cubixmc.server.network.packets.play.PacketOutEntityLookandRelativeMove;
import org.cubixmc.server.network.packets.play.PacketOutEntityRelativeMove;
import org.cubixmc.server.util.BoundingBox;
import org.cubixmc.server.util.Movement;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector3D;
import org.cubixmc.util.Vector3F;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class CubixEntity implements Entity, Runnable {
    private static int ENTITY_ID = 0;

    protected final Random random = new Random();
    protected final int entityId;
    protected final Metadata metadata;
    private final UUID uuid;
    protected CubixWorld world;
    protected Position position;
    protected BoundingBox boundingBox;

    private final Movement movement;
    private boolean rotChanged;
    private boolean hasMoved;

    protected CubixEntity(CubixWorld world) {
        this.world = world;
        this.entityId = ENTITY_ID++;
        this.uuid = UUID.randomUUID();
        this.metadata = new Metadata();
        this.movement = new Movement(this);
    }

    protected void setBounds(Vector3D dimensions) {
        setBounds(dimensions, new Vector3F(0.5F, 0.0F, 0.5F));
    }

    protected void setBounds(Vector3D dimensions, Vector3F origin) {
        this.boundingBox = new BoundingBox(this, dimensions, origin);
    }

    public List<CubixEntity> getNearEntities(double radius) {
        List<CubixEntity> entities = world.getEntitiesInArea(position, radius);
        entities.remove(this);
        return entities;
    }

    public boolean isSpawned() {
        return position != null;
    }

    public boolean spawn(Position position) {
        if(this.position != null) {
            return false;
        }

        this.position = position;
        movement.reset(position); // Reset movement tracking
        if(boundingBox != null) {
            boundingBox.update(); // Update bounding box
        }

        metadata.set(0, (byte) 0);
        world.addEntity(this); // Register entity in world
        if(!(this instanceof CubixPlayer)) {
            for(PacketOut packet : getSpawnPackets()) {
                CubixServer.broadcast(packet, world, null);
            }
        }

        return true;
    }

    public void move(double dx, double dy, double dz) {
        position.add(dx, dy, dz);
        this.hasMoved = true;
    }

    public void setYaw(float yaw) {
        setRotation(yaw, position.getPitch());
    }

    public void setPitch(float pitch) {
        setRotation(position.getYaw(), pitch);
    }

    public void setRotation(float yaw, float pitch) {
        position.setYaw(yaw);
        position.setPitch(pitch);
        rotChanged = true;
    }

    @Override
    public final void run() {
        tick();
    }

    public void tick() {
        if(hasMoved && boundingBox != null) {
            // Update bounding box
            boundingBox.update();
        }

        if(hasMoved || rotChanged) {
            // Update clients
            PacketOut packet;
            if(hasMoved && rotChanged) {
                packet = new PacketOutEntityLookandRelativeMove(this, movement.update());
            } else if(hasMoved) {
                packet = new PacketOutEntityRelativeMove(this, movement.update());
            } else {
                packet = new PacketOutEntityLook(this);
            }

            CubixServer.broadcast(packet, world, this);
            if(rotChanged && this instanceof Player) {
                // For players, update head rot
                CubixServer.broadcast(new PacketOutEntityHeadLook(entityId, MathHelper.byteToDegree(position.getYaw())), world, this);
            }

            this.hasMoved = rotChanged = false;
        }
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
        return position.clone();
    }

    @Override
    public boolean teleport(Position to) {
        if(!position.getWorld().equals(to.getWorld())) {
            return false;
        }

        position.setX(to.getX());
        position.setY(to.getY());
        position.setZ(to.getZ());
        position.setYaw(to.getYaw());
        position.setPitch(to.getPitch());
        movement.reset(position);
        return true;
    }

    @Override
    public boolean teleport(org.cubixmc.entity.Entity target) {
        return teleport(target.getPosition());
    }

    public abstract List<PacketOut> getSpawnPackets();
}
