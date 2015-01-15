package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutPlayerListHeaderFooter extends PacketOut {
    private String header;
    private String footer;

    public PacketOutPlayerListHeaderFooter() {
        super(0x47);
    }

    public PacketOutPlayerListHeaderFooter(String header, String footer) {
        super(0x47);
        this.header = header;
        this.footer = footer;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeChat(header);
        codec.writeChat(footer);
    }
}
