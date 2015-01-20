package org.cubixmc.server.network;

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
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.Position;

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

    public void sendPacket(PacketOut packet) {
        channel.writeAndFlush(packet);
    }

    public void sendPacket(PacketOut packet, GenericFutureListener<ChannelFuture> future) {
        channel.writeAndFlush(packet).addListener(future);
    }

    public void play(UUID uuid, String name) {
        CubixPlayer player = new CubixPlayer(null, this, uuid, name);
        player.spawn(new Position(null, 0, 80, 0));
        CubixServer.getInstance().addPlayer(player);
        setPlayer(player);

        CubixWorld world = CubixServer.getInstance().getMainWorld();
        Position spawn = world.getSpawnPosition();

        setPhase(Phase.PLAY);
        PacketOutJoinGame packet = new PacketOutJoinGame();
        packet.setEntityID(player.getEntityId());
        packet.setGamemode(0);
        packet.setDimension(0);
        packet.setDifficulty(0);
        packet.setMaxPlayers(60);
        packet.setLevelType("default");
        packet.setReducedDebugInfo(false);
        sendPacket(packet);

        PacketOutSpawnPosition packet2 = new PacketOutSpawnPosition();
        packet2.setLocation(spawn);
        sendPacket(packet2);

        PacketOutPlayerAbilities packet3 = new PacketOutPlayerAbilities();
        packet3.setFlyingSpeed(0.2f);
        packet3.setWalkingSpeed(0.2f);
        packet3.setFlags(6);
        sendPacket(packet3);

        // Send the chunks
        int cx = ((int) spawn.getX()) >> 4;
        int cz = ((int) spawn.getZ()) >> 4;
        int radius = 4;
        sendPacket(world.getChunk(cx, cz).getPacket());
        for(int dx = -radius; dx <= radius; dx++) {
            for(int dz = -radius; dz <= radius; dz++) {
                CubixChunk chunk = world.getChunk(cx + dx, cz + dz);
                if(chunk != null) {
                    sendPacket(chunk.getPacket());
                }
            }
        }

        PacketOutPlayerPositionLook packet4 = new PacketOutPlayerPositionLook();
        packet4.setX(spawn.getX());
        packet4.setY(spawn.getY());
        packet4.setZ(spawn.getZ());
        packet4.setYaw(spawn.getYaw());
        packet4.setPitch(spawn.getPitch());
        packet4.setRelativePos(false);
        packet4.setRelativeLook(false);
        sendPacket(packet4);

        for(CubixPlayer p : CubixServer.getInstance().getOnlinePlayers()){
            p.sendMessage(ChatColor.AQUA + name + " has joined!");
            spawnPlayer(this, p);
            spawnPlayer(p.getConnection(), player);
        }
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
