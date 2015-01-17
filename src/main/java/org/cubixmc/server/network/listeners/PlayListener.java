package org.cubixmc.server.network.listeners;

import org.cubixmc.chat.ChatColor;
import org.cubixmc.entity.Player;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.play.PacketInChatMessage;
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
        //connection.getPlayer().sendMessage("Your ping is: " + ChatColor.GREEN + ping);
    }


    public void onChat(PacketInChatMessage packet) {
        String sender = connection.getPlayer().getName();
        for(Player p : CubixServer.getInstance().getOnlinePlayers()){
            p.sendMessage(sender + " : " + packet.getMessage());
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
