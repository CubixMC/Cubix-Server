package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSignEditorOpen extends PacketOut {
    private Position location;

    public PacketOutSignEditorOpen(Position location) {
        super(0x36);
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
    }
}
