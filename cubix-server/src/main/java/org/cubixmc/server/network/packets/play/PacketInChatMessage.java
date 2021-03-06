package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.bukkit.command.defaults.KickCommand;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInChatMessage extends PacketIn {
    private String message;

    public PacketInChatMessage() {
        super(0x01);
    }

    @Override
    public void decode(Codec codec) {
        this.message = codec.readString();
    }

    @Override
    public void handle(Connection connection) {
        connection.getPlayer().chat(message);
//        connection.getPlayer().getWorld().refreshChunks(connection.getPlayer());
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
