package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnExperienceOrb extends PacketOut {
    private int entityID;
    private int x;
    private int y;
    private int z;
    private short count;

    public PacketOutSpawnExperienceOrb() {
        super(0x11);
    }

    public PacketOutSpawnExperienceOrb(int entityID, int x, int y, int z, short count) {
        super(0x11);
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeInt(z);
        codec.writeShort(count);
    }
}
