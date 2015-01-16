package org.cubixmc.server.network.packets.login;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEncryptionRequest extends PacketOut {
    private String serverID;
    private int length;
    private byte[] publicKey;
    private int length0;
    private byte[] verifyToken;

    public PacketOutEncryptionRequest() {
        super(0x01);
    }

    public PacketOutEncryptionRequest(String serverID, int length, byte[] publicKey, int length0, byte[] verifyToken) {
        super(0x01);
        this.serverID = serverID;
        this.length = length;
        this.publicKey = publicKey;
        this.length0 = length0;
        this.verifyToken = verifyToken;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(serverID);
//        codec.writeVarInt(length);
        codec.writeBytes(publicKey);
//        codec.writeVarInt(length0);
        codec.writeBytes(verifyToken);
    }
}
