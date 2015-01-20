package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

/**
 * This packet is sent when a player swings his arm.
 * This is one of the very few classes i could not auto generate because my generator failed to parse the fields.
 * It has none...
 */
@Getter
public class PacketIntAnimation extends PacketOut {

    public PacketIntAnimation() {
        super(0x0A);
    }

    @Override
    public void encode(Codec codec) {
    }
}
