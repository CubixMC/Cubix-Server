package org.cubixmc.server.network.packets.play;

public class PacketOutTab-Complete extends PacketOut{
private int count;
private String match;

public PacketOutTab-Complete(int countString match){
        super(0x3A);
        this.count=count;
        this.match=match;
        }

@Override
public void encode(Codec codec){
        codec.writeVarInt(count);
        codec.writeString(match);
        }
        }
