package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutScoreboardObjective extends PacketOut {
    private String objectiveName;
    private int mode;
    private String objectiveValue;
    private String type;

    public PacketOutScoreboardObjective() {
        super(0x3B);
    }

    public PacketOutScoreboardObjective(String objectiveName, int mode, String objectiveValue, String type) {
        super(0x3B);
        this.objectiveName = objectiveName;
        this.mode = mode;
        this.objectiveValue = objectiveValue;
        this.type = type;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(objectiveName);
        codec.writeByte(mode);
        codec.writeString(objectiveValue);
        codec.writeString(type);
    }
}
