package org.cubixmc.server.network.packets.login;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEncryptionRequest extends PacketOut {
    private String serverID;
    private byte[] publicKey;
    private byte[] verifyToken;

    public PacketOutEncryptionRequest() {
        super(0x01);
    }

    public PacketOutEncryptionRequest(String serverID, byte[] publicKey, byte[] verifyToken) {
        super(0x01);
        this.serverID = serverID;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(serverID);
        codec.writeBytes(publicKey);
        codec.writeBytes(verifyToken);
    }
}
