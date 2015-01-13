package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
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

    @Override
    public void handle() {
    }
}
