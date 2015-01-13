package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnMob extends PacketOut {
    private Metadata metadata;
    private int x;
    private int y;
    private int entityID;
    private int z;
    private short velocityX;
    private int pitch;
    private int type;
    private short velocityZ;
    private int headPitch;
    private short velocityY;
    private int yaw;

    public PacketOutSpawnMob() {
        super(0x0F);
    }

    public PacketOutSpawnMob(Metadata metadataint xint yint entityIDint zshort velocityXint pitchint typeshort velocityZint headPitchshort velocityYint yaw) {
        super(0x0F);
        this.metadata = metadata;
        this.x = x;
        this.y = y;
        this.entityID = entityID;
        this.z = z;
        this.velocityX = velocityX;
        this.pitch = pitch;
        this.type = type;
        this.velocityZ = velocityZ;
        this.headPitch = headPitch;
        this.velocityY = velocityY;
        this.yaw = yaw;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeMetadata(metadata);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeVarInt(entityID);
        codec.writeInt(z);
        codec.writeShort(velocityX);
        codec.writeByte(pitch);
        codec.writeByte(type);
        codec.writeShort(velocityZ);
        codec.writeByte(headPitch);
        codec.writeShort(velocityY);
        codec.writeByte(yaw);
    }
}
