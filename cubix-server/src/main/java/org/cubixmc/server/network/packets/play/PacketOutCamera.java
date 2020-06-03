package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutCamera extends PacketOut {
    private int cameraID;

    public PacketOutCamera() {
        super(0x43);
    }

    public PacketOutCamera(int cameraID) {
        super(0x43);
        this.cameraID = cameraID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(cameraID);
    }
}
