package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Vector3D;
import org.cubixmc.util.Vector3I;

@Getter
public class PacketInTabComplete extends PacketIn {
    private String text;
    private boolean hasPosition;
    private Vector3D lookedAtBlock;

    public PacketInTabComplete() {
        super(0x14);
    }

    @Override
    public void decode(Codec codec) {
        this.text = codec.readString();
        this.hasPosition = codec.readBoolean();
        this.lookedAtBlock = codec.readPosition();
    }

    @Override
    public void handle(Connection connection) {
    }
}
