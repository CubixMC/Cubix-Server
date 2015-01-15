package org.cubixmc.server.network.packets.login;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInEncryptionResponse extends PacketIn {
    private int length;
    private byte[] sharedSecret;
    private int length0;
    private byte[] verifyToken;

    public PacketInEncryptionResponse() {
        super(0x01);
    }

    @Override
    public void decode(Codec codec) {
        this.length = codec.readVarInt();
        this.sharedSecret = codec.readBytes();
        this.length0 = codec.readVarInt();
        this.verifyToken = codec.readBytes();
    }

    @Override
    public void handle() {
    }
}
