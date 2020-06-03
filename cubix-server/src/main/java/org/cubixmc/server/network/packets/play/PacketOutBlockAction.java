package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutBlockAction extends PacketOut {
    private Position location;
    private int byte1;
    private int byte2;
    private int blockType;

    public PacketOutBlockAction() {
        super(0x24);
    }

    public PacketOutBlockAction(Position location, int byte1, int byte2, int blockType) {
        super(0x24);
        this.location = location;
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.blockType = blockType;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
        codec.writeByte(byte1);
        codec.writeByte(byte2);
        codec.writeVarInt(blockType);
    }
}
