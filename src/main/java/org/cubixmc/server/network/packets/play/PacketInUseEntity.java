package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInUseEntity extends PacketIn {
    private int target;
    private int type;
    private float targetX;
    private float targetY;
    private float targetZ;

    public PacketInUseEntity() {
        super(0x02);
    }

    @Override
    public void decode(Codec codec) {
        this.target = codec.readVarInt();
        this.type = codec.readVarInt();
        this.targetX = codec.readFloat();
        this.targetY = codec.readFloat();
        this.targetZ = codec.readFloat();
    }

    @Override
    public void handle(Connection connection) {
    }
}
