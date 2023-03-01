package org.cubixmc.world;

import org.cubixmc.util.Vector2I;

public interface Chunk {

    World getWorld();

    void load();

    void unload();

    int getX();

    int getZ();

    Vector2I getPosition();
}
