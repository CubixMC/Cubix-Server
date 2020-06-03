package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateScore extends PacketOut {
    private String scoreName;
    private int updateRemove;
    private String objectiveName;
    private int value;

    public PacketOutUpdateScore() {
        super(0x3C);
    }

    public PacketOutUpdateScore(String scoreName, int updateRemove, String objectiveName, int value) {
        super(0x3C);
        this.scoreName = scoreName;
        this.updateRemove = updateRemove;
        this.objectiveName = objectiveName;
        this.value = value;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(scoreName);
        codec.writeByte(updateRemove);
        codec.writeString(objectiveName);
        codec.writeVarInt(value);
    }
}
