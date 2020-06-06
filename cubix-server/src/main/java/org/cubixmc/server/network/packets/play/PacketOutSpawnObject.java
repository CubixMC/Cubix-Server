package org.cubixmc.server.network.packets.play;

import org.bukkit.Location;
import org.cubixmc.entity.EntityType;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Vector3I;

public class PacketOutSpawnObject extends PacketOut {
    private int entityID;
    private EntityType type;
    private Vector3I position;
    private int pitch;
    private int yaw;
    private int data;
    private Vector3I velocity;

    public PacketOutSpawnObject() {
        super(0x0E);
    }

    public PacketOutSpawnObject(CubixEntity entity, int data) {
        this(entity.getEntityId(), EntityType.DROPPED_ITEM, toFixedPoint(entity.getPosition()),
                MathHelper.byteToDegree(entity.getPosition().getYaw()),
                MathHelper.byteToDegree(entity.getPosition().getPitch()), data);
        if(data > 0) {
            // TODO: Entity velocity
            this.velocity = new Vector3I(0, 0, 0);
        }
    }

    public PacketOutSpawnObject(int entityID, EntityType type, Vector3I position, int yaw, int pitch, int data) {
        super(0x0E);
        this.entityID = entityID;
        this.type = type;
        this.position = position;
        this.yaw = yaw;
        this.pitch = pitch;
        this.data = data;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(type.getTypeId());
        codec.writeInt(position.getX());
        codec.writeInt(position.getY());
        codec.writeInt(position.getZ());
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeInt(data);
        if(velocity != null) {
            codec.writeShort(velocity.getX());
            codec.writeShort(velocity.getY());
            codec.writeShort(velocity.getZ());
        }
    }

    private static Vector3I toFixedPoint(Location position) {
        return new Vector3I(
                position.getX() * 32.0,
                position.getY() * 32.0,
                position.getZ() * 32.0
        );
    }
}
