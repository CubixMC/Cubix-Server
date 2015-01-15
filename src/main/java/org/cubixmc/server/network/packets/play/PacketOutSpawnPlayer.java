package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

import java.util.UUID;

@Data
public class PacketOutSpawnPlayer extends PacketOut {
    private int entityID;
    private UUID playerUUID;
    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;
    private short currentItem;
    private Metadata metadata;

    public PacketOutSpawnPlayer() {
        super(0x0C);
    }

    public PacketOutSpawnPlayer(int entityID, UUID playerUUID, int x, int y, int z, int yaw, int pitch, short currentItem, Metadata metadata) {
        super(0x0C);
        this.entityID = entityID;
        this.playerUUID = playerUUID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.currentItem = currentItem;
        this.metadata = metadata;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeUUID(playerUUID);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeInt(z);
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeShort(currentItem);
        codec.writeMetadata(metadata);
    }
}
