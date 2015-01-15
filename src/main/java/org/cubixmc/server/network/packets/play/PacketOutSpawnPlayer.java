package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnPlayer extends PacketOut {
    private UUID playerUUID;
    private Metadata metadata;
    private int x;
    private int y;
    private int entityID;
    private int z;
    private int pitch;
    private short currentItem;
    private int yaw;

    public PacketOutSpawnPlayer() {
        super(0x0C);
    }

    public PacketOutSpawnPlayer(UUID playerUUID, Metadata metadata, int x, int y, int entityID, int z, int pitch, short currentItem, int yaw) {
        super(0x0C);
        this.playerUUID = playerUUID;
        this.metadata = metadata;
        this.x = x;
        this.y = y;
        this.entityID = entityID;
        this.z = z;
        this.pitch = pitch;
        this.currentItem = currentItem;
        this.yaw = yaw;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeUUID(playerUUID);
        codec.writeMetadata(metadata);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeVarInt(entityID);
        codec.writeInt(z);
        codec.writeByte(pitch);
        codec.writeShort(currentItem);
        codec.writeByte(yaw);
    }
}
