package org.cubixmc.server.network.packets.login;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEncryptionRequest extends PacketOut {
    private byte[] verifyToken;
    private int length0;
    private int length;
    private byte[] publicKey;
    private String serverID;

    public PacketOutEncryptionRequest() {
        super(0x01);
    }

    public PacketOutEncryptionRequest(byte[] verifyTokenint length0int lengthbyte[]publicKeyString serverID) {
        super(0x01);
        this.verifyToken = verifyToken;
        this.length0 = length0;
        this.length = length;
        this.publicKey = publicKey;
        this.serverID = serverID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeBytes(verifyToken);
        codec.writeVarInt(length0);
        codec.writeVarInt(length);
        codec.writeBytes(publicKey);
        codec.writeString(serverID);
    }
}
