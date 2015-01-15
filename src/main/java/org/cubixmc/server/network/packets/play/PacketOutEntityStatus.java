package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityStatus extends PacketOut {
    private int entityID;
    private int entityStatus;

    public PacketOutEntityStatus() {
        super(0x1A);
    }

    public PacketOutEntityStatus(int entityID, int entityStatus) {
        super(0x1A);
        this.entityID = entityID;
        this.entityStatus = entityStatus;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(entityID);
        codec.writeByte(entityStatus);
    }
}
