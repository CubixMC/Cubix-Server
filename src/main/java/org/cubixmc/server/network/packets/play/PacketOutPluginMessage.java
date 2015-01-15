package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutPluginMessage extends PacketOut {
private byte[] data;
private String channel;

public PacketOutPluginMessage() {
super(0x3F);
}

public PacketOutPluginMessage(byte[] data, String channel) {
super(0x3F);
this.data = data;
this.channel = channel;
}

@Override
public void encode(Codec codec) {
codec.writeBytes(data);
codec.writeString(channel);
}
}
