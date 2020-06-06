package org.cubixmc.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ConcurrentSet;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.codecs.CodecHandler;
import org.cubixmc.server.network.codecs.CompletionHandler;
import org.cubixmc.server.network.codecs.DummyHandler;
import org.cubixmc.server.network.codecs.PacketHandler;
import org.cubixmc.server.threads.Threads;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class NetManager extends ChannelInitializer<SocketChannel> {
    public static final AttributeKey<byte[]> VERIFY_TOKEN = AttributeKey.valueOf("verifyToken");
    public static final AttributeKey<String> USERNAME = AttributeKey.valueOf("username");
    public static final int NETWORK_LIMIT = 2097152;

    private final Set<Connection> connections = ConcurrentHashMap.newKeySet();
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;
    private final InetSocketAddress address;
    private Channel channel;

    public NetManager(InetSocketAddress address) {
        if(Epoll.isAvailable()) {
            CubixServer.getLogger().log(Level.INFO, "Using linux native epoll networking.");
            this.bossGroup = new EpollEventLoopGroup();
            this.workerGroup = new EpollEventLoopGroup();
        } else {
            this.bossGroup = new NioEventLoopGroup();
            this.workerGroup = new NioEventLoopGroup();
        }
        this.bootstrap = new ServerBootstrap();
        this.address = address;
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(this)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void connect() {
        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(25565)).addListener((GenericFutureListener<ChannelFuture>) future -> {
            if(!future.isSuccess()) {
                CubixServer.getLogger().log(Level.SEVERE, "Failed to bind port " + address.getPort(), future.cause());
                Threads.mainThread.execute(() -> CubixServer.getInstance().stop());
            } else {
                CubixServer.getLogger().log(Level.INFO, "Network pipeline running!");
            }
        });
        this.channel = channelFuture.channel();
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        Connection connection = new Connection(channel);
        PacketHandler packetHandler = new PacketHandler();
        connection.setPacketHandler(packetHandler);
        connection.setPhase(Phase.HANDSHAKE);
        connections.add(connection);

        channel.pipeline().addLast("encryption", DummyHandler.INSTANCE); // encrypt/decrypt packets
        channel.pipeline().addLast("completion", new CompletionHandler()); // parse packet length
        channel.pipeline().addLast("compression", DummyHandler.INSTANCE); // compress/decompress packets
        channel.pipeline().addLast("codec", new CodecHandler(connection)); // encode/decode packets
        channel.pipeline().addLast("handler", packetHandler); // read packets in player thread
    }

    public Connection getConnection(Channel channel) {
        Connection connection = null;
        for(Connection con : connections) {
            if(con.getChannel() == channel) {
                connection = con;
                break;
            }
        }

        return connection;
    }

    public Connection[] getConnections() {
        return connections.toArray(new Connection[0]);
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    public void shutdown() {
        try {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            CubixServer.getLogger().log(Level.WARNING, "Unexpected interruption while shutting down network pipeline", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
