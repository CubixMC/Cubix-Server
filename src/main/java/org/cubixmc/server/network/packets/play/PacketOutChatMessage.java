package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutChatMessage extends PacketOut {
    private int position;
    private String jSONData;

    public PacketOutChatMessage(int positionString jSONData) {
        super(0x02);
        this.position = position;
        this.jSONData = jSONData;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(position);
        codec.writeString(jSONData);
    }
}
