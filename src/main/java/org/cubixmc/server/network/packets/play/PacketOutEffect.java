package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutEffect extends PacketOut {
    private int effectID;
    private Position location;
    private int data;
    private boolean disableRelativeVolume;

    public PacketOutEffect() {
        super(0x28);
    }

    public PacketOutEffect(int effectID, Position location, int data, boolean disableRelativeVolume) {
        super(0x28);
        this.effectID = effectID;
        this.location = location;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(effectID);
        codec.writePosition(location);
        codec.writeInt(data);
        codec.writeBool(disableRelativeVolume);
    }
}
