package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

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

    public PacketOutSpawnPlayer(UUID playerUUIDMetadata metadataint xint yint entityIDint zint pitchshort currentItemint yaw) {
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
