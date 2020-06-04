package org.cubixmc.server.network.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.PacketIn;

import java.net.InetSocketAddress;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;

public class PacketHandler extends SimpleChannelInboundHandler<PacketIn> {
    private final Deque<PacketIn> packetQueue = new ConcurrentLinkedDeque<>();
    private Connection connection;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketIn packet) throws Exception {
        if(connection.getPhase() == Phase.PLAY && !packet.isAsync()) {
            packetQueue.offer(packet);
        } else {
            packet.handle(connection);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        CubixServer.getLogger().log(Level.SEVERE, "Exception occurred in network codec during stage " + connection.getPhase(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CubixServer.getInstance().getNetManager().removeConnection(connection);
        if(connection.getPhase() == Phase.PLAY) {
            CubixServer.getInstance().removePlayer(connection.getPlayer());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CubixServer.getLogger().log(Level.INFO, ((InetSocketAddress) ctx.channel().remoteAddress()).getHostName() + " Connected");
        connection = CubixServer.getInstance().getNetManager().getConnection(ctx.channel());
    }

    public void execute() {
        PacketIn packet;
        while((packet = packetQueue.poll()) != null) {
//            CubixServer.getLogger().log(Level.INFO, "Handling " + packet.getClass().getSimpleName());
            packet.handle(connection);
        }
    }
}
