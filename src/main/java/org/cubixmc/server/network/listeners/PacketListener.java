package org.cubixmc.server.network.listeners;

import com.google.common.collect.Maps;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

public abstract class PacketListener {
    private final Map<Class<?>, Method> classMethodMap = Maps.newHashMap();
    protected final Connection connection;

    public PacketListener(Connection connection) {
        this.connection = connection;
        for(Method method : getClass().getMethods()) {
            if(method.getParameterTypes().length > 0) {
                Class<?> type = method.getParameterTypes()[0];
                if(PacketIn.class.isAssignableFrom(type)) {
                    classMethodMap.put(type, method);
                }
            }
        }
    }

    public void call(PacketIn packet) {
        Method method = classMethodMap.get(packet.getClass());
        if(method != null) {
            try {
                method.invoke(this, packet);
            } catch(Exception e) {
                CubixServer.getLogger().log(Level.SEVERE, "Failed to dispatch incoming packet", e);
            }
        }
    }

    public abstract boolean isAsync();
}
