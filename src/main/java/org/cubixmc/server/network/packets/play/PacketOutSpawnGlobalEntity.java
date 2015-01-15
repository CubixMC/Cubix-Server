package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnGlobalEntity extends PacketOut {
    private int entityID;
    private int type;
    private int x;
    private int y;
    private int z;

    public PacketOutSpawnGlobalEntity() {
        super(0x2C);
    }

    public PacketOutSpawnGlobalEntity(int entityID, int type, int x, int y, int z) {
        super(0x2C);
        this.entityID = entityID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(type);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeInt(z);
    }
}
