package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSoundEffect extends PacketOut {
    private String soundName;
    private int effectPositionX;
    private int effectPositionY;
    private int effectPositionZ;
    private float volume;
    private int pitch;

    public PacketOutSoundEffect() {
        super(0x29);
    }

    public PacketOutSoundEffect(String soundName, int effectPositionX, int effectPositionY, int effectPositionZ, float volume, int pitch) {
        super(0x29);
        this.soundName = soundName;
        this.effectPositionX = effectPositionX;
        this.effectPositionY = effectPositionY;
        this.effectPositionZ = effectPositionZ;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(soundName);
        codec.writeInt(effectPositionX);
        codec.writeInt(effectPositionY);
        codec.writeInt(effectPositionZ);
        codec.writeFloat(volume);
        codec.writeByte(pitch);
    }
}
