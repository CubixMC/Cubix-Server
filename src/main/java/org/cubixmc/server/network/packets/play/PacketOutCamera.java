package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutCamera extends PacketOut {
    private int cameraID;

    public PacketOutCamera(int cameraID) {
        super(0x43);
        this.cameraID = cameraID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(cameraID);
    }
}
