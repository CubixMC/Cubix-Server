package org.cubixmc.server.network;

import com.google.common.collect.Lists;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cubixmc.chat.ChatColor;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.entity.Player;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.codecs.CompressionHandler;
import org.cubixmc.server.network.codecs.DummyHandler;
import org.cubixmc.server.network.codecs.EncryptionHandler;
import org.cubixmc.server.network.codecs.PacketHandler;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.login.PacketOutDisconnect;
import org.cubixmc.server.network.packets.login.PacketOutSetCompression;
import org.cubixmc.server.network.packets.play.*;
import org.cubixmc.server.util.QueuedChunk;
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

@Data
@RequiredArgsConstructor
public class Connection {
    private final SocketChannel channel;
    private int compression = -1;
    private Phase phase;
    private CubixPlayer player;
    private PacketHandler packetHandler;

    public void sendPackets(PacketOut... packets) {
        for(PacketOut packet : packets) {
            sendPacket(packet);
        }
    }

    public void sendPacket(PacketOut packet) {
        channel.writeAndFlush(packet);
    }

    public void sendPacket(PacketOut packet, GenericFutureListener<ChannelFuture> future) {
        channel.writeAndFlush(packet).addListener(future);
    }

    public void play(UUID uuid, String name) {
        setPhase(Phase.PLAY);
        CubixWorld world = CubixServer.getInstance().getMainWorld();
        CubixPlayer player = new CubixPlayer(world, this, uuid, name);
        player.spawn(new Position(null, 0, 80, 0));
        CubixServer.getInstance().addPlayer(player);
        setPlayer(player);

        // Make player spawn in
        Position spawn = world.getSpawnPosition();
        player.spawn(spawn);
    }

    public void disconnect(String message) {
        message = ChatColor.replace('&', message);
        ChatMessage chatMessage = ChatMessage.fromString(message);
        switch(phase) {
            case LOGIN:
                channel.writeAndFlush(new PacketOutDisconnect(chatMessage.toString())).addListener(ChannelFutureListener.CLOSE);
                break;
            case PLAY:
                channel.writeAndFlush(
                        new org.cubixmc.server.network.packets.play.PacketOutDisconnect(chatMessage.toString()))
                        .addListener(ChannelFutureListener.CLOSE);
                break;
            default:
                channel.close();
        }

    }

    public void setCompression(final int compression) {
        GenericFutureListener<ChannelFuture> listener = new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(Connection.this.compression < 0 && compression >= 0) {
                    channel.pipeline().replace("compression", "compression", new CompressionHandler(Connection.this));
                } else if(Connection.this.compression >= 0 && compression < 0) {
                    channel.pipeline().replace("compression", "compression", DummyHandler.INSTANCE);
                }

                Connection.this.compression = compression;
            }
        };

        switch(phase) {
            case LOGIN:
                sendPacket(new PacketOutSetCompression(compression), listener);
                break;
            case PLAY:
                sendPacket(new org.cubixmc.server.network.packets.play.PacketOutSetCompression(compression), listener);
                break;
            default:
                throw new IllegalStateException("Cannot set compression during " + phase.toString() + "!");
        }
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void enableEncryption(SecretKey key) {
        channel.pipeline().replace("encryption", "encryption", new EncryptionHandler(key));
    }

    public void disableEncryption() {
        channel.pipeline().replace("encryption", "encryption", DummyHandler.INSTANCE);
    }

    private void spawnPlayer(Connection connection, CubixPlayer player) {
        connection.sendPacket(player.getSpawnPacket());
    }
}
