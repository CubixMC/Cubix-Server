package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutWindowProperty extends PacketOut {
private short property;
private short value;
private int windowID;

public PacketOutWindowProperty() {
super(0x31);
}

public PacketOutWindowProperty(short property, short value, int windowID) {
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
