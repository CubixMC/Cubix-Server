package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutRemoveEntityEffect extends PacketOut {
    private int effectID;
    private int entityID;

    public PacketOutRemoveEntityEffect() {
        super(0x1E);
    }

    public PacketOutRemoveEntityEffect(int effectIDint entityID) {
        super(0x1E);
        this.effectID = effectID;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(effectID);
        codec.writeVarInt(entityID);
    }
}
