package org.cubixmc.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.server.network.NetManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
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

    private final NetManager netManager;

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

        try {
            this.netManager = new NetManager(new InetSocketAddress(InetAddress.getLocalHost(), 25565));
        } catch(UnknownHostException e) {
            logger.log(Level.SEVERE, "Failed to find host to start server with!", e);
            throw new RuntimeException("");
        }
    }

    public void tick() {
        // This is the main thread
    }
}
