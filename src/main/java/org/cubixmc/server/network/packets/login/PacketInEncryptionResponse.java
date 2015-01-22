package org.cubixmc.server.network.packets.login;

import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.server.threads.AuthenticationThread;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Getter
public class PacketInEncryptionResponse extends PacketIn {
    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketInEncryptionResponse() {
        super(0x01);
    }

    @Override
    public void decode(Codec codec) {
        this.sharedSecret = codec.readBytes();
        this.verifyToken = codec.readBytes();
    }

    @Override
    public void handle(Connection connection) {
        String username = connection.getChannel().attr(NetManager.USERNAME).getAndRemove();
        byte[] actualToken = connection.getChannel().attr(NetManager.VERIFY_TOKEN).getAndRemove();

        // Very username
        if(username == null || username.isEmpty()) {
            connection.disconnect("No username has been specified yet!");
            return;
        }
        if(!username.equals(username.replaceAll("[^a-zA-Z0-9_]", ""))) {
            connection.disconnect("Your username contains illegal characters!");
            return;
        }
        if(username.length() > 16) {
            connection.disconnect("Your username is too long! HAX?");
            return;
        }

        KeyPair keyPair = CubixServer.getInstance().getKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            if(!Arrays.equals(actualToken, cipher.doFinal(verifyToken))) {
                CubixServer.getLogger().log(Level.WARNING, "Verify token does not match the original token!");
                connection.disconnect("Invalid verify token!");
                return;
            }

            byte[] secretKeyBytes = cipher.doFinal(sharedSecret);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
            connection.enableEncryption(secretKey);
            connection.setCompression(256);

            new AuthenticationThread(connection, username, secretKey);
        } catch(GeneralSecurityException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to decrypt data from packet", e);
            connection.disconnect("Failed to decrypt your packet data!");
        }
    }
}
