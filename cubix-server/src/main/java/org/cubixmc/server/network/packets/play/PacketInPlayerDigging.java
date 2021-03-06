package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Vector3D;
import org.cubixmc.util.Vector3I;

@Getter
public class PacketInPlayerDigging extends PacketIn {
    private int status;
    private Vector3D location;
    private int face;

    public PacketInPlayerDigging() {
        super(0x07);
    }

    @Override
    public void decode(Codec codec) {
        this.status = codec.readByte();
        this.location = codec.readPosition();
        this.face = codec.readByte();
    }

    @Override
    public void handle(Connection connection) {
        Vector3I pos = new Vector3I(location);
        switch(status) {
            case 0:
                // Start digging
                break;
            case 1:
                // Cancel digging
                break;
            case 2:
                // Finish digging
                connection.getPlayer().getWorld().breakNaturally(pos.getX(), pos.getY(), pos.getZ());
                break;
            case 3:
                // Drop item stack
                break;
            case 4:
                // Drop item
                break;
            case 5:
                // Shoot arrow or finish eating
                break;
            default:
                throw new IllegalArgumentException("Invalid block dig action: " + status);
        }
    }
}
