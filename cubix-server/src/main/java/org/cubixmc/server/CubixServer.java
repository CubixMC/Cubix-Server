package org.cubixmc.server;

import io.netty.util.ResourceLeakDetector;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.threads.Threads;
import org.cubixmc.server.util.ForwardLogHandler;
import org.cubixmc.server.world.CubixWorld;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
            if(exempt != null && player.equals(exempt)) {
                continue;
            }

            player.getConnection().sendPacket(packet);
        }
    }

    public static void main(String[] args) {
        // TODO: Parse args
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        int port = 25565;
        if(args.length >= 2 && args[0].equalsIgnoreCase("--port")) {
            port = Integer.parseInt(args[1]);
        }

        new CubixServer(port);
    }

    private final Map<String, CubixWorld> worlds = new ConcurrentHashMap<>();
    private final Map<UUID, CubixPlayer> players = new ConcurrentHashMap<>();
    private final @Getter NetManager netManager;
    private @Getter KeyPair keyPair;
    private CubixTerminal terminal;

    public CubixServer(int port) {
        setInstance(this);
        setLogger(Logger.getLogger("Cubix"));
        // Load property file here....
        // Cant bother atm

//        LogManager.getRootLogger();
//        PatternLayout layout = PatternLayout.newBuilder()
//                .withPattern("[%d{HH:mm:ss} %level]: %msg%n")
//                .build();
//        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger())
//                .addAppender(TerminalConsoleAppender.createAppender(TerminalConsoleAppender.PLUGIN_NAME, null, layout, false));

        // We actually use log4j2 because Minecraft uses it too.
        // However i prefer java logging so we use a forward handler
        logger.setUseParentHandlers(false);
        for(Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        logger.addHandler(new ForwardLogHandler());

        // Start terminal
        this.terminal = new CubixTerminal();
        new Thread(terminal::start).start();

        Properties properties = new Properties();
        try(InputStream input = CubixServer.class.getResourceAsStream("/cubix.properties")) {
            properties.load(input);
            String mcVersion = properties.getProperty("minecraft.version");
            String ciBuild = properties.getProperty("ci.build");
            logger.log(Level.INFO, "Starting CubixMC " + mcVersion + " (Build #" + ciBuild + ")");
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Failed to load version metadata", e);
        }

        logger.log(Level.INFO, "Generating key pair...");
        generateKeyPair();

        logger.log(Level.INFO, "Starting a Minecraft protocol server on port " + port);
        this.netManager = new NetManager(new InetSocketAddress(port));
        netManager.connect();

        Threads.mainThread.scheduleAtFixedRate(this, 0L, 50L, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, "Loading world world....");
        CubixWorld world = new CubixWorld("world");
        worlds.put(world.getName(), world);
        world.load();
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            // This is the main thread
            for(Connection connection : netManager.getConnections()) {
                connection.getPacketHandler().execute();
            }

            // Entity tick
            for(final CubixWorld world : worlds.values()) {
                world.tickEntities();
            }

            // Tick the worlds
            for(final CubixWorld world : worlds.values()) {
                world.tick();
            }

            long end = System.currentTimeMillis();
            if((end - start) > 1) {
                getLogger().log(Level.INFO, "Tick duration: " + (end - start) + "ms");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unhandled exception in main thread", e);
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

    public void stop() {
        logger.log(Level.INFO, "Stopping server.");
        terminal.stop();
        Threads.mainThread.shutdown();

        logger.log(Level.INFO, "Closing network pipeline.");
        netManager.shutdown();

        logger.log(Level.INFO, "Goodbye!");
    }
}
