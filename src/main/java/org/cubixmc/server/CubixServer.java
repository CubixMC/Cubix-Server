package org.cubixmc.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.NetManager;
import org.cubixmc.server.threads.Threads;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class
 */
public class CubixServer {
    private static @Getter @Setter(AccessLevel.PRIVATE) CubixServer instance;
    private static @Getter @Setter(AccessLevel.PRIVATE) Logger logger;

    public static void main(String[] args) {
        // TODO: Parse args
        new CubixServer();
    }

    private final @Getter NetManager netManager;
    private @Getter KeyPair keyPair;

    public CubixServer() {
        setInstance(this);
        setLogger(Logger.getLogger("Cubix"));
        // Load property file here....
        // Cant bother atm

        try {
            FileHandler fileHandler = new FileHandler("server.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Failed to initiate logger handler", e);
        }

        logger.log(Level.INFO, "Generating key pair...");
        generateKeyPair();

        try {
            logger.log(Level.INFO, "Starting a Minecraft protocol server on localhost:25565");
            this.netManager = new NetManager(new InetSocketAddress(InetAddress.getLocalHost(), 25565));
            netManager.connect();

            Threads.mainThread.start();
        } catch(UnknownHostException e) {
            logger.log(Level.SEVERE, "Failed to find host to start server with!", e);
            throw new RuntimeException("");
        }
    }

    public void tick() {
        // This is the main thread
        for(Connection connection : netManager.getConnections()) {
            connection.getPacketHandler().execute();
        }
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
