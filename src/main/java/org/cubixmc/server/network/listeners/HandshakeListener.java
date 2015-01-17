package org.cubixmc.server.network.listeners;

import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.Phase;
import org.cubixmc.server.network.packets.handshake.PacketInHandshake;

public class HandshakeListener extends PacketListener {

    public HandshakeListener(Connection connection) {
        super(connection);
    }

    public void onHandshake(PacketInHandshake packet) {
        System.out.println("Test");
        connection.setPhase(Phase.LOGIN);
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
