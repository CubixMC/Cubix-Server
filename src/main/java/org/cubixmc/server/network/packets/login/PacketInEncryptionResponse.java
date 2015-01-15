package org.cubixmc.server.network.packets.login;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInEncryptionResponse extends PacketIn {
private byte[] verifyToken;
private int length0;
private int length;
private byte[] sharedSecret;

public PacketInEncryptionResponse() {
super(0x01);
}

@Override
public void decode(Codec codec) {
this.verifyToken = codec.readBytes();
this.length0 = codec.readVarInt();
this.length = codec.readVarInt();
this.sharedSecret = codec.readBytes();
}

@Override
public void handle() {
}
}
