package org.cubixmc.server.network.packets.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInRequest extends PacketIn {

    public PacketInRequest() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
    }

    @Override
    public void handle(Connection connection) {
        // For now use default values
        JsonObject object = new JsonObject();

        // Server version
        JsonObject version = new JsonObject();
        version.addProperty("name", "Cubix 1.8");
        version.addProperty("protocol", 47);

        // Players
        JsonObject players = new JsonObject();
        players.addProperty("max", 20);
        players.addProperty("online", CubixServer.getInstance().getOnlinePlayers().size());
        JsonArray sample = new JsonArray();
        for(CubixPlayer player : CubixServer.getInstance().getOnlinePlayers()) {
            JsonObject info = new JsonObject();
            info.addProperty("name", player.getName());
            info.addProperty("id", player.getUniqueId().toString());
            sample.add(info);
        }
        players.add("sample", sample);

        object.add("version", version);
        object.add("players", players);
        object.addProperty("description", "Cubix alpha server");
        connection.sendPacket(new PacketOutResponse(object.toString()));
    }
}
