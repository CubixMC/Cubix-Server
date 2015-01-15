package org.cubixmc.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.codecs.CodecHandler;
import org.cubixmc.server.network.codecs.CompletionHandler;
import org.cubixmc.server.network.codecs.DummyHandler;
import org.cubixmc.server.network.codecs.PacketHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class NetManager extends ChannelInitializer<SocketChannel> {
    private final List<Connection> connections = new ArrayList<>();
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;
    private final InetSocketAddress address;

    public NetManager(InetSocketAddress address) {
        this.bossGroup = new NioEventLoopGroup(4);
        this.workerGroup = new NioEventLoopGroup(4);
        this.bootstrap = new ServerBootstrap();
        this.address = address;
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(this)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void connect() {
        bootstrap.bind(address).addListener(new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(!future.isSuccess()) {
                    CubixServer.getLogger().log(Level.SEVERE, "Failed to bind port " + address.getPort(), future.cause());
                    // TODO: Stop server
                }
            }
        });
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        Connection connection = new Connection(channel);
        connections.add(connection);

        channel.pipeline().addLast("encryption", DummyHandler.INSTANCE); // encrypt/decrypt packets
        channel.pipeline().addLast("completion", new CompletionHandler()); // parse packet length
        channel.pipeline().addLast("compression", DummyHandler.INSTANCE); // compress/decompress packets
        channel.pipeline().addLast("codec", new CodecHandler(connection)); // encode/decode packets
        channel.pipeline().addLast("handler", new PacketHandler(connection)); // read packets in player thread
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
}
