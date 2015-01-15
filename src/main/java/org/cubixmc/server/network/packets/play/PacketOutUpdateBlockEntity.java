package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutUpdateBlockEntity extends PacketOut {
    private Position location;
    private int action;
    private byte[] nBTData;

    public PacketOutUpdateBlockEntity() {
        super(0x35);
    }

    public PacketOutUpdateBlockEntity(Position location, int action, byte[] nBTData) {
        super(0x35);
        this.location = location;
        this.action = action;
        this.nBTData = nBTData;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
        codec.writeByte(action);
        codec.writeBytes(nBTData);
    }
}
