package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutRemoveEntityEffect extends PacketOut {
    private int entityID;
    private int effectID;

    public PacketOutRemoveEntityEffect() {
        super(0x1E);
    }

    public PacketOutRemoveEntityEffect(int entityID, int effectID) {
        super(0x1E);
        this.entityID = entityID;
        this.effectID = effectID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(effectID);
    }
}
