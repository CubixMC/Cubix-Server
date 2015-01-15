package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutTimeUpdate extends PacketOut {
private long ageOfTheWorld;
private long timeOfDay;

public PacketOutTimeUpdate() {
super(0x03);
}

public PacketOutTimeUpdate(long ageOfTheWorld, long timeOfDay) {
super(0x03);
this.ageOfTheWorld = ageOfTheWorld;
this.timeOfDay = timeOfDay;
}

@Override
public void encode(Codec codec) {
codec.writeLong(ageOfTheWorld);
codec.writeLong(timeOfDay);
}
}
