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
        packet2.setLocation(new Position(null, 0, 80, 0));
        sendPacket(packet2);

        PacketOutPlayerAbilities packet3 = new PacketOutPlayerAbilities();
        packet3.setFlyingSpeed(0.2f);
        packet3.setWalkingSpeed(0.2f);
        packet3.setFlags(6);
        sendPacket(packet3);

        PacketOutPlayerPositionLook packet4 = new PacketOutPlayerPositionLook();
        packet4.setX(0);
        packet4.setY(80);
        packet4.setZ(0);
        packet4.setYaw(0f);
        packet4.setPitch(0f);
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

    public void setCompression(int compression) {
        switch(phase) {
            case LOGIN:
                sendPacket(new PacketOutSetCompression(compression));
                break;
            case PLAY:
                sendPacket(new org.cubixmc.server.network.packets.play.PacketOutSetCompression(compression));
                break;
            default:
                throw new IllegalStateException("Cannot set compression during " + phase.toString() + "!");
        }

        if(this.compression < 0 && compression >= 0) {
            channel.pipeline().replace("compression", "compression", new CompressionHandler(this));
        } else if(this.compression >= 0 && compression < 0) {
            channel.pipeline().replace("compression", "compression", DummyHandler.INSTANCE);
        }

        this.compression = compression;
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
