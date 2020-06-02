package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

@Getter
public class PacketInPluginMessage extends PacketIn {
    private String channel;
    private byte[] data;

    public PacketInPluginMessage() {
        super(0x17);
    }

    @Override
    public void decode(Codec codec) {
        this.channel = codec.readString();
        this.data = codec.remainingBytes();
        CubixServer.getLogger().log(Level.INFO, "Plugin message on channel {0}: {1}", new Object[]{
                channel, new String(data, StandardCharsets.UTF_8)
        });
    }

    @Override
    public void handle(Connection connection) {
    }
}
