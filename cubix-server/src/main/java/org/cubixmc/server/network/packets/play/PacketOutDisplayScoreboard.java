package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutDisplayScoreboard extends PacketOut {
    private int position;
    private String scoreName;

    public PacketOutDisplayScoreboard() {
        super(0x3D);
    }

    public PacketOutDisplayScoreboard(int position, String scoreName) {
        super(0x3D);
        this.position = position;
        this.scoreName = scoreName;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(position);
        codec.writeString(scoreName);
    }
}
