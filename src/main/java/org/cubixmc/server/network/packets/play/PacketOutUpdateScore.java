package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateScore extends PacketOut {
    private int update
    /Remove;
    private int value;
    private String objectiveName;
    private String scoreName;

    public PacketOutUpdateScore() {
        super(0x3C);
    }

    public PacketOutUpdateScore(int update/Remove, int value, String objectiveName, String scoreName) {
        super(0x3C);
        this.update / Remove = update / Remove;
        this.value = value;
        this.objectiveName = objectiveName;
        this.scoreName = scoreName;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(update / Remove);
        codec.writeVarInt(value);
        codec.writeString(objectiveName);
        codec.writeString(scoreName);
    }
}
