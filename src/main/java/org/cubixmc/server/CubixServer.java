package org.cubixmc.server;

import com.google.common.collect.Maps;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutSpawnPlayer;
import org.cubixmc.server.threads.Threads;
import org.cubixmc.server.util.ForwardLogHandler;
import org.cubixmc.server.world.CubixWorld;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class
 */
public class CubixServer implements Runnable {
    private static @Getter @Setter(AccessLevel.PRIVATE) CubixServer instance;
    private static @Getter @Setter(AccessLevel.PRIVATE) Logger logger;

    public static void broadcast(PacketOut packet, CubixWorld world, CubixEntity exempt) {
        for(CubixPlayer player : instance.getOnlinePlayers()) {
            if(player.equals(exempt)) {
                continue;
            }

            player.getConnection().sendPacket(packet);
        }
    }

    public static void main(String[] args) {
        // TODO: Parse args
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
        new CubixServer();
    }

    private final Map<String, CubixWorld> worlds = new ConcurrentHashMapV8<>();
    private final Map<UUID, CubixPlayer> players = Maps.newConcurrentMap();
    private final @Getter NetManager netManager;
    private @Getter KeyPair keyPair;

    public CubixServer() {
        setInstance(this);
        setLogger(Logger.getLogger("Cubix"));
        // Load property file here....
        // Cant bother atm

        // We actually use log4j2 because Minecraft uses it too.
        // However i prefer java logging so we use a forward handler
        logger.setUseParentHandlers(false);
        for(Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        logger.addHandler(new ForwardLogHandler());

        logger.log(Level.INFO, "Generating key pair...");
        generateKeyPair();

        try {
            logger.log(Level.INFO, "Starting a Minecraft protocol server on localhost:25565");
            this.netManager = new NetManager(new InetSocketAddress(InetAddress.getLocalHost(), 25565));
            netManager.connect();
        } catch(UnknownHostException e) {
            logger.log(Level.SEVERE, "Failed to find host to start server with!", e);
            throw new RuntimeException("");
        }

        Threads.mainThread.scheduleAtFixedRate(this, 0L, 50L, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, "Loading world world....");
        CubixWorld world = new CubixWorld("world");
        worlds.put(world.getName(), world);
        world.load();
    }

    @Override
    public void run() {
        // This is the main thread
        for(Connection connection : netManager.getConnections()) {
            connection.getPacketHandler().execute();
        }

        // Entity tick (for now just players)
        for(final CubixPlayer player : players.values()) {
            Threads.entityExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    player.tick();
                }
            });
        }
    }

    public void addPlayer(CubixPlayer player) {
        players.put(player.getUniqueId(), player);
    }

    public void removePlayer(CubixPlayer player) {
        players.remove(player.getUniqueId());
    }

    public Collection<CubixPlayer> getOnlinePlayers() {
        return Collections.unmodifiableCollection(players.values());
    }

    public List<CubixWorld> getWorldList() {
        return new ArrayList<>(worlds.values());
    }

    public CubixWorld getMainWorld() {
        return getWorldList().get(0);
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            this.keyPair = generator.generateKeyPair();
        } catch(NoSuchAlgorithmException e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to generate RSA-Key for encryption");
        }
    }
}
