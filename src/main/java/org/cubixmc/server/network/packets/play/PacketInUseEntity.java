package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInUseEntity extends PacketIn {
    private float targetZ;
    private float targetY;
    private float targetX;
    private int type;
    private int target;

    public PacketInUseEntity() {
        super(0x02);
    }

    @Override
    public void decode(Codec codec) {
        this.targetZ = codec.readFloat();
        this.targetY = codec.readFloat();
        this.targetX = codec.readFloat();
        this.type = codec.readVarInt();
        this.target = codec.readVarInt();
    }

    @Override
    public void handle() {
    }
}
