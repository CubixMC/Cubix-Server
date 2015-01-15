package org.cubixmc.server.network;

import io.netty.channel.socket.SocketChannel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.codecs.CompressionHandler;
import org.cubixmc.server.network.codecs.DummyHandler;
import org.cubixmc.server.network.codecs.EncryptionHandler;
import org.cubixmc.server.network.codecs.PacketHandler;
import org.cubixmc.server.network.listeners.PacketListener;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.login.PacketOutSetCompression;

import java.util.logging.Level;

import javax.crypto.SecretKey;

@Data
@RequiredArgsConstructor
public class Connection {
    private final SocketChannel channel;
    private int compression = -1;
    private Phase phase = Phase.HANDSHAKE;
    private CubixPlayer player;
    private PacketHandler packetHandler;
    private PacketListener listener;

    public void sendPacket(PacketOut packet) {
        channel.writeAndFlush(packet);
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

        // Update protocol listener
        String name = phase.toString().substring(0, 1) + phase.toString().substring(1).toLowerCase();
        try {
            Class<?> listenerClass = Class.forName("org.cubixmc.server.network.listeners." + name + "Listener");
            setListener((PacketListener) listenerClass.newInstance());
        } catch(Exception e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to initiate protocol listener", e);
        }
    }

    public void enableEncryption(SecretKey key) {
        channel.pipeline().replace("encryption", "encryption", new EncryptionHandler(key));
    }

    public void disableEncryption() {
        channel.pipeline().replace("encryption", "encryption", DummyHandler.INSTANCE);
    }
}
