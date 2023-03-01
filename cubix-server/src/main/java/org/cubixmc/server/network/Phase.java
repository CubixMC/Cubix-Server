package org.cubixmc.server.network;

import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.packets.PacketIn;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public enum Phase {
    HANDSHAKE,
    LOGIN,
    STATUS,
    PLAY;

    private final Map<Integer, Class<? extends PacketIn>> idToClass = new ConcurrentHashMap<>();

    private Phase() {
        try {
            Reflections reflections = new Reflections("org.cubixmc.server.network.packets." + toString().toLowerCase());
            for(Class<? extends PacketIn> clazz : reflections.getSubTypesOf(PacketIn.class)) {
                PacketIn test = clazz.newInstance();
                idToClass.put(test.getId(), clazz);
            }
        } catch(Exception e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to detect packet classes for phase "+ toString(), e);
        }
    }

    public PacketIn getPacket(int id) {
        Class<? extends PacketIn> packetClass = idToClass.get(id);
        if(packetClass != null) {
            try {
                return packetClass.newInstance();
            } catch(Exception e) {
                return null; // Impossible
            }
        } else {
            return null;
        }
    }
}
