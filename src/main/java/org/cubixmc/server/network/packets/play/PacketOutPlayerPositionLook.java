package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutPlayerPositionLook extends PacketOut {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean relativePos;
    private boolean relativeLook;

    public PacketOutPlayerPositionLook() {
        super(0x08);
    }

    public PacketOutPlayerPositionLook(Position position) {
        this(position.getX(), position.getY(), position.getZ(), position.getYaw(), position.getPitch());
    }

    public PacketOutPlayerPositionLook(double x, double y, double z) {
        this(x, y, z, 0f, 0f);
    }

    public PacketOutPlayerPositionLook(double x, double y, double z, float yaw, float pitch) {
        this(x, y, z, yaw, pitch, false, false);
    }

    public PacketOutPlayerPositionLook(double x, double y, double z, float yaw, float pitch, boolean relativePos, boolean relativeLook) {
        super(0x08);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.relativePos = relativePos;
        this.relativeLook = relativeLook;
    }

    @Override
    public void encode(Codec codec) {
        int flags = 0;
        if(relativePos) {
            flags += 0x01 + 0x02 + 0x04;
        }
        if(relativeLook) {
            flags += 0x08 + 0x10;
        }

        codec.writeDouble(x);
        codec.writeDouble(y);
        codec.writeDouble(z);
        codec.writeFloat(yaw);
        codec.writeFloat(pitch);
        codec.writeByte(flags);
    }
}
