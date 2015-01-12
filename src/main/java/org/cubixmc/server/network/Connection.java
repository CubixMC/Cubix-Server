package org.cubixmc.server.network;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Connection {
    private final Channel channel;
    private int compression;
    private Phase phase = Phase.HANDSHAKE;

    public void sendPacket(Object packet) {
        channel.writeAndFlush(packet);
    }
}
