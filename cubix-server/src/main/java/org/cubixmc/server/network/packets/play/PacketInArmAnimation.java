package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

/**
 * This packet is sent when a player swings his arm.
 * This is one of the very few classes i could not auto generate because my generator failed to parse the fields.
 * It has none...
 */
@Getter
public class PacketInArmAnimation extends PacketIn {

    public PacketInArmAnimation() {
        super(0x0A);
    }

    @Override
    public void decode(Codec codec) {
    }

    @Override
    public void handle(Connection connection) {
    }
}
