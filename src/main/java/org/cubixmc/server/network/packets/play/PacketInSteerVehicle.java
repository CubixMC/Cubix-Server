package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInSteerVehicle extends PacketIn {
    private float forward;
    private int flags;
    private float sideways;

    public PacketInSteerVehicle() {
        super(0x0C);
    }

    @Override
    public void decode(Codec codec) {
        this.forward = codec.readFloat();
        this.flags = codec.readByte();
        this.sideways = codec.readFloat();
    }

    @Override
    public void handle() {
    }
}