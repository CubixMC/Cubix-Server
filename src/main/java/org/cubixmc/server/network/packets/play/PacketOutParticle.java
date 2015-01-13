package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutParticle extends PacketOut {
    private float offsetX;
    private float offsetZ;
    private float offsetY;
    private int[] data;
    private float particleData;
    private float x;
    private float y;
    private float z;
    private int particleId;
    private boolean longDistance;
    private int numberOfParticles;

    public PacketOutParticle(float offsetXfloat offsetZfloat offsetYint[]datafloat particleDatafloat xfloat yfloat zint particleIdboolean longDistanceint numberOfParticles) {
        super(0x2A);
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.offsetY = offsetY;
        this.data = data;
        this.particleData = particleData;
        this.x = x;
        this.y = y;
        this.z = z;
        this.particleId = particleId;
        this.longDistance = longDistance;
        this.numberOfParticles = numberOfParticles;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeFloat(offsetX);
        codec.writeFloat(offsetZ);
        codec.writeFloat(offsetY);
        codec.writeVarInts(data);
        codec.writeFloat(particleData);
        codec.writeFloat(x);
        codec.writeFloat(y);
        codec.writeFloat(z);
        codec.writeInt(particleId);
        codec.writeBoolean(longDistance);
        codec.writeInt(numberOfParticles);
    }
}
