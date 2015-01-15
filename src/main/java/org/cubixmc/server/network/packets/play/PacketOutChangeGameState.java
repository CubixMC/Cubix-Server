package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChangeGameState extends PacketOut {
private int reason;
private float value;

public PacketOutChangeGameState() {
super(0x2B);
}

public PacketOutChangeGameState(int reason, float value) {
super(0x2B);
this.reason = reason;
this.value = value;
}

@Override
public void encode(Codec codec) {
codec.writeByte(reason);
codec.writeFloat(value);
}
}
