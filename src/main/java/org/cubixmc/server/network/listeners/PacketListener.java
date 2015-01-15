package org.cubixmc.server.network.listeners;

import com.google.common.collect.Maps;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.packets.PacketIn;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

public abstract class PacketListener {
    private final Map<Class<?>, Method> classMethodMap = Maps.newHashMap();

    public PacketListener() {
        for(Method method : getClass().getMethods()) {
            if(method.getParameterTypes().length > 0) {
                Class<?> type = method.getParameterTypes()[0];
                if(type.isAssignableFrom(PacketIn.class)) {
                    classMethodMap.put(type, method);
                }
            }
        }
    }

    public void call(PacketIn packet) {
        Method method = classMethodMap.get(packet);
        if(method != null) {
            try {
                method.invoke(this, packet);
            } catch(Exception e) {
                CubixServer.getLogger().log(Level.SEVERE, "Failed to dispatch incoming packet", e);
            }
        }
    }
}
