package org.cubixmc.server.network.listeners;

import org.cubixmc.server.network.Connection;

public class PlayListener extends PacketListener {

    public PlayListener(Connection connection) {
        super(connection);
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
