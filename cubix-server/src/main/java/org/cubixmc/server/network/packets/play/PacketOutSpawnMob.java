package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.entity.Metadata;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnMob extends PacketOut {
    private int entityID;
    private int type;
    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;
    private int headPitch;
    private short velocityX;
    private short velocityY;
    private short velocityZ;
    private Metadata metadata;

    public PacketOutSpawnMob() {
        super(0x0F);
    }

    public PacketOutSpawnMob(int entityID, int type, int x, int y, int z, int yaw, int pitch, int headPitch, short velocityX, short velocityY, short velocityZ, Metadata metadata) {
        super(0x0F);
        this.entityID = entityID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.metadata = metadata;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(type);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeInt(z);
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeByte(headPitch);
        codec.writeShort(velocityX);
        codec.writeShort(velocityY);
        codec.writeShort(velocityZ);
        codec.writeMetadata(metadata);
    }
}
