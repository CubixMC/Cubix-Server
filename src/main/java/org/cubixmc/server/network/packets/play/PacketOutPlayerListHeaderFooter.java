package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutPlayerListHeaderFooter extends PacketOut {
    private String footer;
    private String header;

    public PacketOutPlayerListHeaderFooter() {
        super(0x47);
    }

    public PacketOutPlayerListHeaderFooter(String footerString header) {
        super(0x47);
        this.footer = footer;
        this.header = header;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeChat(footer);
        codec.writeChat(header);
    }
}
