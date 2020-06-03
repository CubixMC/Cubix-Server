package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityTeleport extends PacketOut {
    private int entityID;
    private int x;
    private int y;
    private int z;
    private int yaw;
    private int pitch;
    private boolean onGround;

    public PacketOutEntityTeleport() {
        super(0x18);
    }

    public PacketOutEntityTeleport(int entityID, int x, int y, int z, int yaw, int pitch, boolean onGround) {
        super(0x18);
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeInt(x);
        codec.writeInt(y);
        codec.writeInt(z);
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeBoolean(onGround);
    }
}
