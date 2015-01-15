package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutBlockAction extends PacketOut {
    private int byte1;
    private int byte2;
    private int blockType;
    private Position location;

    public PacketOutBlockAction() {
        super(0x24);
    }

    public PacketOutBlockAction(int byte1, int byte2, int blockType, Position location) {
        super(0x24);
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.blockType = blockType;
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(byte1);
        codec.writeByte(byte2);
        codec.writeVarInt(blockType);
        codec.writePosition(location);
    }
}
