package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityLook extends PacketOut {
    private int entityID;
    private int yaw;
    private int pitch;
    private boolean onGround;

    public PacketOutEntityLook() {
        super(0x16);
    }

    public PacketOutEntityLook(int entityID, int yaw, int pitch, boolean onGround) {
        super(0x16);
        this.entityID = entityID;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeBoolean(onGround);
    }
}
