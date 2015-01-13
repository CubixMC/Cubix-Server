package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutEffect extends PacketOut {
    private int effectID;
    private int data;
    private Position location;
    private boolean disableRelativeVolume;

    public PacketOutEffect(int effectIDint dataPosition locationboolean disableRelativeVolume) {
        super(0x28);
        this.effectID = effectID;
        this.data = data;
        this.location = location;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(effectID);
        codec.writeInt(data);
        codec.writePosition(location);
        codec.writeBoolean(disableRelativeVolume);
    }
}
