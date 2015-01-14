package org.cubixmc.server.network;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.entity.CubixPlayer;

@Data
@RequiredArgsConstructor
public class Connection {
    private final Channel channel;
    private int compression;
    private Phase phase = Phase.HANDSHAKE;
    private CubixPlayer player;

    public void sendPacket(Object packet) {
        channel.writeAndFlush(packet);
    }
}
