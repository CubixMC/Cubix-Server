package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutUpdateBlockEntity extends PacketOut {
    private byte[] nBTData;
    private int action;
    private Position location;

    public PacketOutUpdateBlockEntity(byte[] nBTDataint actionPosition location) {
        super(0x35);
        this.nBTData = nBTData;
        this.action = action;
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeBytes(nBTData);
        codec.writeByte(action);
        codec.writePosition(location);
    }
}
