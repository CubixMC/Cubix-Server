package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Vector3D;

@Getter
public class PacketInUpdateSign extends PacketIn {
    private Vector3D location;
    private BaseComponent[] line1;
    private BaseComponent[] line2;
    private BaseComponent[] line3;
    private BaseComponent[] line4;

    public PacketInUpdateSign() {
        super(0x12);
    }

    @Override
    public void decode(Codec codec) {
        this.location = codec.readPosition();
        this.line1 = codec.readChat();
        this.line2 = codec.readChat();
        this.line3 = codec.readChat();
        this.line4 = codec.readChat();
    }

    @Override
    public void handle(Connection connection) {
    }
}
