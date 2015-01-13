package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutCollectItem extends PacketOut {
    private int collectedEntityID;
    private int collectorEntityID;

    public PacketOutCollectItem(int collectedEntityIDint collectorEntityID) {
        super(0x0D);
        this.collectedEntityID = collectedEntityID;
        this.collectorEntityID = collectorEntityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(collectedEntityID);
        codec.writeVarInt(collectorEntityID);
    }
}
