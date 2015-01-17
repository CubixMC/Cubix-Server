package org.cubixmc.server.network.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.server.threads.Threads;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class PacketHandler extends SimpleChannelInboundHandler<PacketIn> {
    private final Queue<PacketIn> packetQueue = new ArrayDeque<>();
    private Connection connection;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketIn packet) throws Exception {
        if(connection.getListener().isAsync()) {
            connection.getListener().call(packet);
        } else {
            packetQueue.offer(packet);
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
        PacketIn[] packets = packetQueue.toArray(new PacketIn[0]);
        packetQueue.clear();
        for(final PacketIn packet : packets) {
            Threads.playerExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    connection.getListener().call(packet);
                }
            });
        }
    }
}
