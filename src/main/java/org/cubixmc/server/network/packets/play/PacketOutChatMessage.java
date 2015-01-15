package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChatMessage extends PacketOut {
    private String jSONData;
    private int position;

    public PacketOutChatMessage() {
        super(0x02);
    }

    public PacketOutChatMessage(String jSONData, int position) {
        super(0x02);
        this.jSONData = jSONData;
        this.position = position;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(jSONData);
        codec.writeByte(position);
    }
}
