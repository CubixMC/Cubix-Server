package org.cubixmc.server.network;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.codecs.CompressionHandler;
import org.cubixmc.server.network.codecs.DummyHandler;
import org.cubixmc.server.network.codecs.EncryptionHandler;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.login.PacketOutSetCompression;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
public class Connection {
    private final @Getter SocketChannel channel;
    private @Getter int compression = -1;
    private @Getter @Setter Phase phase = Phase.HANDSHAKE;
    private @Getter @Setter CubixPlayer player;

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

    public void enableEncryption(SecretKey key) {
        channel.pipeline().replace("encryption", "encryption", new EncryptionHandler(key));
    }

    public void disableEncryption() {
        channel.pipeline().replace("encryption", "encryption", DummyHandler.INSTANCE);
    }
}
