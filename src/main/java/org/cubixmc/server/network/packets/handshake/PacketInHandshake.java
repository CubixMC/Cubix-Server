package org.cubixmc.server.network.packets.handshake;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInHandshake extends PacketIn {
    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private int nextState;

    public PacketInHandshake() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.protocolVersion = codec.readVarInt();
        this.serverAddress = codec.readString();
        this.serverPort = codec.readShort();
        this.nextState = codec.readVarInt();
    }

    @Override
    public void handle() {
    }
}
