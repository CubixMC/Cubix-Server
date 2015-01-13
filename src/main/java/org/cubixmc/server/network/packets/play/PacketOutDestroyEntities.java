package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutDestroyEntities extends PacketOut {
    private int count;
    private int[] entityIDs;

    public PacketOutDestroyEntities(int countint[]entityIDs) {
        super(0x13);
        this.count = count;
        this.entityIDs = entityIDs;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(count);
        codec.writeVarInts(entityIDs);
    }
}
