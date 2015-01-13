package org.cubixmc.server.network;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.packets.PacketIn;

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
            ClassPath classPath = ClassPath.from(getClass().getClassLoader());
            for(ClassInfo classInfo : classPath.getTopLevelClassesRecursive("org.cubixmc.server.network.packets." + toString().toLowerCase())) {
                Class<?> clazz = Class.forName(classInfo.getName());
                if(clazz.isAssignableFrom(PacketIn.class)) {
                    PacketIn test = (PacketIn) clazz.newInstance();
                    idToClass.put(test.getId(), (Class<? extends PacketIn>) clazz);
                }
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
