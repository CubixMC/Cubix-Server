package org.cubixmc.server.network.packets.handshake;

import com.google.gson.JsonParser;
import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.PacketIn;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.logging.Level;

@Getter
public class PacketInHandshake extends PacketIn {
    private static final JsonParser JSON_PARSER = new JsonParser();
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
        CubixServer.getLogger().log(Level.INFO, "Player connecting from " + serverAddress);

        // Bungeecord support
        String[] context = serverAddress.split("\00");
        if(context.length == 3 || context.length == 4) {
            this.serverAddress = context[0];
            connection.setSocketAddress(new InetSocketAddress(context[1], 25565));
            connection.setSpoofedUUID(context[2]);
        }
        if(context.length == 4) {
            connection.setSpoofedProfile(JSON_PARSER.parse(context[3]));
        }
    }
}
