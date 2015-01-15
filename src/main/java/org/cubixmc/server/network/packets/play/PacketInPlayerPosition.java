package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPlayerPosition extends PacketIn {
private boolean onGround;
private double feetY;
private double x;
private double z;

public PacketInPlayerPosition() {
super(0x04);
}

@Override
public void decode(Codec codec) {
this.onGround = codec.readBoolean();
this.feetY = codec.readFloat();
this.x = codec.readFloat();
this.z = codec.readFloat();
}

@Override
public void handle() {
}
}
