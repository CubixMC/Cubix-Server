package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSoundEffect extends PacketOut {
    private float volume;
    private int effectPositionZ;
    private int effectPositionY;
    private int effectPositionX;
    private int pitch;
    private String soundName;

    public PacketOutSoundEffect(float volumeint effectPositionZint effectPositionYint effectPositionXint pitchString soundName) {
        super(0x29);
        this.volume = volume;
        this.effectPositionZ = effectPositionZ;
        this.effectPositionY = effectPositionY;
        this.effectPositionX = effectPositionX;
        this.pitch = pitch;
        this.soundName = soundName;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeFloat(volume);
        codec.writeInt(effectPositionZ);
        codec.writeInt(effectPositionY);
        codec.writeInt(effectPositionX);
        codec.writeByte(pitch);
        codec.writeString(soundName);
    }
}
