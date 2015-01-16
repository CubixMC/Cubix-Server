package org.cubixmc.server.network.packets.login;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInEncryptionResponse extends PacketIn {
    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketInEncryptionResponse() {
        super(0x01);
    }

    @Override
    public void decode(Codec codec) {
        this.sharedSecret = codec.readBytes();
        this.verifyToken = codec.readBytes();
    }

    @Override
    public void handle() {
    }
}
