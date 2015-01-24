package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.server.network.packets.play.PacketOutPlayerListItem.ListAction;

import java.util.Arrays;

@Getter
public class PacketInKeepAlive extends PacketIn {
    private int keepAliveID;

    public PacketInKeepAlive() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.keepAliveID = codec.readVarInt();
    }

    @Override
    public void handle(Connection connection) {
        long ping = System.currentTimeMillis() - connection.getPlayer().getKeepAliveCount();
        if(!connection.getPlayer().getKeepAliveIds().remove(keepAliveID)) {
//            connection.disconnect("Invalid KeepAlive id!");
//            return;
        }

        connection.getPlayer().setPing((int) ping);
        PacketOutPlayerListItem packet = new PacketOutPlayerListItem(ListAction.UPDATE_PING, Arrays.asList(connection.getPlayer()));
        for(CubixPlayer player : CubixServer.getInstance().getOnlinePlayers()) {
            player.getConnection().sendPacket(packet);
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
