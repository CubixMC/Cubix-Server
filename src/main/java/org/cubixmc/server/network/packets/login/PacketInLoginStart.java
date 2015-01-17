package org.cubixmc.server.network.packets.login;

import io.netty.util.Attribute;
import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.PacketIn;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Random;
import java.util.logging.Level;

@Getter
public class PacketInLoginStart extends PacketIn {
    private static final Random random = new Random();
    private String name;

    public PacketInLoginStart() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.name = codec.readString();
    }

    @Override
    public void handle(Connection connection) {
        KeyPair keyPair = CubixServer.getInstance().getKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        byte[] encodedPubKey = pubKey.getEncoded();

        // Prevent fake name exploit
        Attribute<String> usernameKey = connection.getChannel().attr(NetManager.USERNAME);
        if(usernameKey.get() != null) {
            usernameKey.set(name);
        } else {
            CubixServer.getLogger().log(Level.WARNING, "Client attempted to send username twice!");
            connection.disconnect("One shall only have one username!");
            return;
        }

        // Generate verify token
        byte[] verifyToken = new byte[4];
        random.nextBytes(verifyToken);
        connection.getChannel().attr(NetManager.VERIFY_TOKEN).set(verifyToken);

        // Process
        PacketOutEncryptionRequest request = new PacketOutEncryptionRequest();
        request.setServerID("");
        request.setPublicKey(encodedPubKey);
        request.setVerifyToken(verifyToken);
        connection.sendPacket(request);
    }
}
