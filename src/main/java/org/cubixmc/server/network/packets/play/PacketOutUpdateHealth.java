package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateHealth extends PacketOut {
private float health;
private int food;
private float foodSaturation;

public PacketOutUpdateHealth() {
super(0x06);
}

public PacketOutUpdateHealth(float health, int food, float foodSaturation) {
super(0x06);
this.health = health;
this.food = food;
this.foodSaturation = foodSaturation;
}

@Override
public void encode(Codec codec) {
codec.writeFloat(health);
codec.writeVarInt(food);
codec.writeFloat(foodSaturation);
}
}
