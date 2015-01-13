package org.cubixmc.server.network.packets.play;

import lombok.Getter;

@Getter
public class PacketInTab-Complete extends PacketIn{
private boolean hasPosition;
private Position lookedAtBlock;
private String text;

public PacketInTab-Complete(){
        super(0x14);
        }

@Override
public void decode(Codec codec){
        this.hasPosition=codec.readBoolean();
        this.lookedAtBlock=codec.readPosition();
        this.text=codec.readString();
        }

@Override
public void handle(){
        }
        }
