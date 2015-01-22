package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

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

        connection.sendPacket(new PacketOutChatMessage(ChatMessage.fromString("Your ping is " + ping).toString(), 0));
        connection.getPlayer().setPing((int) ping);
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
