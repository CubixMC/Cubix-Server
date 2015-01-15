package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutScoreboardObjective extends PacketOut {
private int mode;
private String objectiveValue;
private String type;
private String objectiveName;

public PacketOutScoreboardObjective() {
super(0x3B);
}

public PacketOutScoreboardObjective(int mode, String objectiveValue, String type, String objectiveName) {
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
