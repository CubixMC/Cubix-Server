package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutWindowProperty extends PacketOut {
    private short property;
    private short value;
    private int windowID;

    public PacketOutWindowProperty(short propertyshort valueint windowID) {
        super(0x31);
        this.property = property;
        this.value = value;
        this.windowID = windowID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeShort(property);
        codec.writeShort(value);
        codec.writeByte(windowID);
    }
}
