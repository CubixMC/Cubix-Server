package org.cubixmc.server.network.listeners;


import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.login.PacketInEncryptionResponse;
import org.cubixmc.server.network.packets.login.PacketInLoginStart;
import org.cubixmc.server.network.packets.login.PacketOutEncryptionRequest;
import org.cubixmc.server.network.packets.login.PacketOutLoginSuccess;
import org.cubixmc.server.threads.AuthenticationThread;

import java.security.*;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoginListener extends PacketListener {
    private static final Random random = new Random();
    private final byte[] verifyToken = new byte[4];
    private String username;

    public LoginListener(Connection connection){
        super(connection);
        random.nextBytes(verifyToken);
    }

    public void onLoginStart(PacketInLoginStart packet) {
        KeyPair keyPair = CubixServer.getInstance().getKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        byte[] encodedPubKey = pubKey.getEncoded();
        if(username == null) {
            this.username = packet.getName();
        } else {
            CubixServer.getLogger().log(Level.WARNING, "Client attempted to send username twice!");
            connection.disconnect("One shall only have one username!");
            return;
        }

        PacketOutEncryptionRequest request = new PacketOutEncryptionRequest();
        request.setServerID("");
        request.setPublicKey(encodedPubKey);
        request.setVerifyToken(verifyToken);
        connection.sendPacket(request);
    }

    public void onEncryptionResponse(PacketInEncryptionResponse packet) {
        KeyPair keyPair = CubixServer.getInstance().getKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            if(!Arrays.equals(verifyToken, cipher.doFinal(packet.getVerifyToken()))) {
                CubixServer.getLogger().log(Level.WARNING, "Verify token does not match the original token!");
                connection.disconnect("Invalid verify token!");
                return;
            }

            byte[] secretKeyBytes = cipher.doFinal(packet.getSharedSecret());
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
            connection.enableEncryption(secretKey);
//            connection.setCompression(256);

            new AuthenticationThread(connection, username, secretKey);
        } catch(GeneralSecurityException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to decrypt data from packet", e);
            connection.disconnect("Failed to decrypt your packet data!");
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
