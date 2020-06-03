package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutWindowProperty extends PacketOut {
    private int windowID;
    private short property;
    private short value;

    public PacketOutWindowProperty() {
        super(0x31);
    }

    public PacketOutWindowProperty(int windowID, short property, short value) {
        super(0x31);
        this.windowID = windowID;
        this.property = property;
        this.value = value;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowID);
        codec.writeShort(property);
        codec.writeShort(value);
    }
}
