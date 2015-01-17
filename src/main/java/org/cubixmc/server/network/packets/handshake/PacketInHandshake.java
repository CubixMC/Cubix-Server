package org.cubixmc.server.network.packets.handshake;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInHandshake extends PacketIn {
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    public PacketInHandshake() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.protocolVersion = codec.readVarInt();
        this.serverAddress = codec.readString();
        this.serverPort = codec.readUnsignedShort();
        this.nextState = codec.readVarInt();
    }

    @Override
    public void handle(Connection connection) {
        connection.setPhase(nextState == 2 ? Phase.LOGIN : Phase.STATUS);
    }
}
