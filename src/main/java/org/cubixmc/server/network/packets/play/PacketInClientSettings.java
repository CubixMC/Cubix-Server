package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInClientSettings extends PacketIn {
    private String locale;
    private int viewDistance;
    private int chatFlags;
    private boolean chatColours;
    private int displayedSkinParts;

    public PacketInClientSettings() {
        super(0x15);
    }

    @Override
    public void decode(Codec codec) {
        this.locale = codec.readString();
        this.viewDistance = codec.readByte();
        this.chatFlags = codec.readByte();
        this.chatColours = codec.readBoolean();
        this.displayedSkinParts = codec.readByte();
    }

    @Override
    public void handle() {
    }
}
