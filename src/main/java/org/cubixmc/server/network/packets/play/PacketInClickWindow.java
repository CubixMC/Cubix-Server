package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.inventory.ClickType;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInClickWindow extends PacketIn {
    private int windowID;
    private short slot;
    private int button;
    private short actionNumber;
    private int mode;
    private ItemStack clickedItem;

    public PacketInClickWindow() {
        super(0x0E);
    }

    @Override
    public void decode(Codec codec) {
        this.windowID = codec.readByte();
        this.slot = codec.readShort();
        this.button = codec.readByte();
        this.actionNumber = codec.readShort();
        this.mode = codec.readByte();
        this.clickedItem = codec.readSlot();
    }

    @Override
    public void handle(Connection connection) {
        CubixPlayer player = connection.getPlayer();
        player.getContainerManager().performInventoryAction(this);
    }
}
