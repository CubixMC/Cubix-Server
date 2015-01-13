package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInEnchantItem extends PacketIn {
    private int enchantment;
    private int windowID;

    public PacketInEnchantItem() {
        super(0x11);
    }

    @Override
    public void decode(Codec codec) {
        this.enchantment = codec.readByte();
        this.windowID = codec.readByte();
    }

    public void handle() {
    }
}
