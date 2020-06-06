package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateSign extends PacketOut {
    private Location location;
    private String line1;
    private String line2;
    private String line3;
    private String line4;

    public PacketOutUpdateSign() {
        super(0x33);
    }

    public PacketOutUpdateSign(Location location, String line1, String line2, String line3, String line4) {
        super(0x33);
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
        codec.writeChat(line1);
        codec.writeChat(line2);
        codec.writeChat(line3);
        codec.writeChat(line4);
    }
}
