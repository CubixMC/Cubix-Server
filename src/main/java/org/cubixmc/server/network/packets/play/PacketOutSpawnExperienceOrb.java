package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnExperienceOrb extends PacketOut {
    private int x;
    private short count;
    private int y;
    private int entityID;
    private int z;

    public PacketOutSpawnExperienceOrb() {
        super(0x11);
    }

    public PacketOutSpawnExperienceOrb(int xshort countint yint entityIDint z) {
        super(0x11);
        this.x = x;
        this.count = count;
        this.y = y;
        this.entityID = entityID;
        this.z = z;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(x);
        codec.writeShort(count);
        codec.writeInt(y);
        codec.writeVarInt(entityID);
        codec.writeInt(z);
    }
}
