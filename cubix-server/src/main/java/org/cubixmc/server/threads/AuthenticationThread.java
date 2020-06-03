package org.cubixmc.server.threads;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.login.PacketOutLoginSuccess;
import org.cubixmc.server.util.auth.GameProfile;

import javax.crypto.SecretKey;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.logging.Level;

public class AuthenticationThread implements Runnable {
    private final Connection connection;
    private final String name;
    private final SecretKey key;

    public AuthenticationThread(Connection connection, String name, SecretKey key) {
        this.connection = connection;
        this.name = name;
        this.key = key;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            byte[][] data = new byte[][] { key.getEncoded(), CubixServer.getInstance().getKeyPair().getPublic().getEncoded() };
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            for(byte[] encrypt : data) {
                digest.update(encrypt);
            }

            String hash = new BigInteger(digest.digest()).toString(16);
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/hasJoined?username=" +
                    URLEncoder.encode(name, "UTF-8") + "&serverId=" + hash);
            HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
            if(httpsConnection.getResponseCode() != 200) {
                CubixServer.getLogger().log(Level.WARNING, "Received invalid response form mojang session server!");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(reader);
            reader.close();

            final GameProfile profile = new GameProfile(object);
            PacketOutLoginSuccess success = new PacketOutLoginSuccess();
            success.setUsername(profile.getName());
            success.setUUID(profile.getUuid().toString());
            System.out.println(success.getUUID() + ": " + success.getUsername());
            connection.sendPacket(success, new GenericFutureListener<ChannelFuture>() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    connection.play(profile);
                }
            });
        } catch(GeneralSecurityException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to authenticate user " + name, e);
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to authenticate user " + name, e);
        }
    }


}
