package org.cubixmc.server.network.packets.login;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.server.util.auth.GameProfile;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Random;
import java.util.UUID;
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
        if(usernameKey.get() == null) {
            usernameKey.set(name);
        } else {
            CubixServer.getLogger().log(Level.WARNING, "Client attempted to send username twice!");
            connection.disconnect("One shall only have one username!");
            return;
        }

        if(true) {
            GameProfile profile = connection.getSpoofedUUID() == null ? createOfflineProfile() : createSpoofedProfile(connection);

            PacketOutLoginSuccess success = new PacketOutLoginSuccess();
            success.setUsername(profile.getName());
            success.setUUID(profile.getUuid().toString().replace("-", ""));

            System.out.println(success.getUUID() + ": " + success.getUsername());
            connection.sendPacket(success, channelFuture -> connection.play(profile));
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

    private GameProfile createSpoofedProfile(Connection connection) {
        JsonObject spoofedProfile = new JsonObject();
        spoofedProfile.addProperty("name", name);
        spoofedProfile.addProperty("id", connection.getSpoofedUUID());
        spoofedProfile.add("properties", connection.getSpoofedProfile());
        return new GameProfile(spoofedProfile);
    }

    private GameProfile createOfflineProfile() {
        return new GameProfile(name, UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()));
    }
}
