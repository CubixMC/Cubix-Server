package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutCloseWindow extends PacketOut {
    private int windowID;

    public PacketOutCloseWindow() {
        super(0x2E);
    }

    public PacketOutCloseWindow(int windowID) {
        super(0x2E);
        this.windowID = windowID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowID);
    }
}
