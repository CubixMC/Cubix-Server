package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

import java.util.Collection;

@Data
public class PacketOutPlayerListItem extends PacketOut {
    private ListAction action;
    private Collection<CubixPlayer> players;

    public PacketOutPlayerListItem(ListAction action, Collection<CubixPlayer> players) {
        this();
        this.action = action;
        this.players = players;
    }

    public PacketOutPlayerListItem() {
        super(0x38);
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(action.ordinal());
        codec.writeVarInt(players.size());
        for(CubixPlayer player : players) {
            codec.writeUUID(player.getUniqueId());
            switch(action) {
                case ADD_PLAYER:
                    codec.writeString(player.getName());
                    codec.writeVarInt(0);
                    codec.writeVarInt(player.getGameMode().ordinal());
                    codec.writeVarInt(player.getPing());
                    codec.writeBoolean(false);
                    break;
                case UPDATE_GAMEMODE:
                    codec.writeVarInt(player.getGameMode().ordinal());
                    break;
                case UPDATE_PING:
                    codec.writeVarInt(player.getPing());
                    break;
                case UPDATE_DISPLAYNAME:
                    codec.writeBoolean(false);
                    break;
                default:
                    break;
            }
        }
    }

    public static enum ListAction {
        ADD_PLAYER,
        UPDATE_GAMEMODE,
        UPDATE_PING,
        UPDATE_DISPLAYNAME,
        REMOVE_PLAYER;
    }
}
