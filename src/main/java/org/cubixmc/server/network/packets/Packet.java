package org.cubixmc.server.network.packets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Packet {
    private final int id;
}
