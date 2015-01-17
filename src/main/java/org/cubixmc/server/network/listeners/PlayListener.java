package org.cubixmc.server.network.listeners;

import org.cubixmc.chat.ChatColor;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.play.PacketInKeepAlive;

public class PlayListener extends PacketListener {

    public PlayListener(Connection connection) {
        super(connection);
    }

    public void onKeepAlive(PacketInKeepAlive packet) {
        int id = packet.getKeepAliveID();
        long ping = System.currentTimeMillis() - connection.getPlayer().getKeepAliveCount();
        if(connection.getPlayer().getKeepAliveId() != id) {
            connection.disconnect("Invalid KeepAlive id!");
            return;
        }

        connection.getPlayer().setPing((int) ping);
        connection.getPlayer().sendMessage("Your ping is: " + ChatColor.GREEN + ping);
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
