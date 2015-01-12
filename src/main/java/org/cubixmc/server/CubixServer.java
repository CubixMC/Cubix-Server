package org.cubixmc.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.server.network.NetManager;

/**
 * Main class
 */
public class CubixServer {
    private static @Getter @Setter(AccessLevel.PRIVATE) CubixServer instance;

    public static void main(String[] args) {
        // TODO: Parse args
        new CubixServer();
    }

    private final NetManager netManager;

    public CubixServer() {
        setInstance(this);
        // Load property file here....
        // Cant bother atm

        this.netManager = new NetManager();
    }

    public void tick() {
        // This is the main thread
    }
}
