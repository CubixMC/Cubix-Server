package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInEnchantItem extends PacketIn {
    private int windowID;
    private int enchantment;

    public PacketInEnchantItem() {
        super(0x11);
    }

    @Override
    public void decode(Codec codec) {
        this.windowID = codec.readByte();
        this.enchantment = codec.readByte();
    }

    @Override
    public void handle() {
    }
}
