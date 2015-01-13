package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutScoreboardObjective extends PacketOut {
    private int mode;
    private String objectiveValue;
    private String type;
    private String objectiveName;

    public PacketOutScoreboardObjective(int modeString objectiveValueString typeString objectiveName) {
        super(0x3B);
        this.mode = mode;
        this.objectiveValue = objectiveValue;
        this.type = type;
        this.objectiveName = objectiveName;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(mode);
        codec.writeString(objectiveValue);
        codec.writeString(type);
        codec.writeString(objectiveName);
    }
}
