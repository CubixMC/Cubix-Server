package org.cubixmc.server.network.packets.handshake;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInHandshake extends PacketIn {
    private String serverAddress;
    private int protocolVersion;
    private int nextState;
    private short serverPort;

    public PacketInHandshake() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.serverAddress = codec.readString();
        this.protocolVersion = codec.readVarInt();
        this.nextState = codec.readVarInt();
        this.serverPort = codec.readShort();
    }

    public void handle() {
    }
}
