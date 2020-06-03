package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutParticle extends PacketOut {
    private int particleId;
    private boolean longDistance;
    private float x;
    private float y;
    private float z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float particleData;
    private int numberOfParticles;
    private int[] data;

    public PacketOutParticle() {
        super(0x2A);
    }

    public PacketOutParticle(int particleId, boolean longDistance, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int numberOfParticles, int[] data) {
        super(0x2A);
        this.particleId = particleId;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.numberOfParticles = numberOfParticles;
        this.data = data;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(particleId);
        codec.writeBoolean(longDistance);
        codec.writeFloat(x);
        codec.writeFloat(y);
        codec.writeFloat(z);
        codec.writeFloat(offsetX);
        codec.writeFloat(offsetY);
        codec.writeFloat(offsetZ);
        codec.writeFloat(particleData);
        codec.writeInt(numberOfParticles);
        codec.writeVarInts(data);
    }
}
