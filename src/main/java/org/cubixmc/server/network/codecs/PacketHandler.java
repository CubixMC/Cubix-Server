package org.cubixmc.server.network.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

import java.util.logging.Level;

@RequiredArgsConstructor
public class PacketHandler extends SimpleChannelInboundHandler<PacketIn> {
    private final Connection connection;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketIn packet) throws Exception {
        CubixServer.getLogger().log(Level.INFO, "Received packet from client: " + packet.getClass().getSimpleName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        CubixServer.getLogger().log(Level.SEVERE, "Exception occurred in network codec during stage " + connection.getPhase(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO: Player quit
    }
}
