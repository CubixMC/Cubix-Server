package org.cubixmc.server.network.packets.login;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutLoginSuccess extends PacketOut {
    private String uUID;
    private String username;

    public PacketOutLoginSuccess() {
        super(0x02);
    }

    public PacketOutLoginSuccess(String uUIDString username) {
        super(0x02);
        this.uUID = uUID;
        this.username = username;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(uUID);
        codec.writeString(username);
    }
}
