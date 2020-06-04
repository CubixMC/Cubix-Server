package org.cubixmc.server.util;

import lombok.Getter;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.world.ChunkSection;
import org.cubixmc.server.world.CubixChunk;

import java.util.Arrays;
import java.util.logging.Level;

@Getter
public class QueuedChunk {
    private final CubixChunk handle;
    private final int x;
    private final int z;
    private final int sections;
    private final byte[] buffer;
    private int index;

    public QueuedChunk(CubixChunk chunk) {
        this.handle = chunk;
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.sections = (1 << chunk.getSectionCount()) - 1;
        this.buffer = new byte[arrayLength(Integer.bitCount(sections), true, true)];
        this.index = 0;
    }

    /**
     * Get the amount of bytes this this will use on the network pipeline.
     *
     * @return Byte size
     */
    public int size() {
        int meta = 4 + 4 + 2; // X(int) + Z(int) + sections(short)
        int bufferSize = buffer.length;
        return meta + bufferSize;
    }

    /**
     * Read data from the chunk and parse it
     */
    public void build() {
        if(index > 0) {
            CubixServer.getLogger().log(Level.WARNING, "Queued chunk is already built");
            return;
        }

        int sectionCount = handle.getSectionCount();
        ChunkSection[] sections = handle.getSections();

        // Write block id & data
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            char[] blocks = section.getBlocks();
            for(int j = 0; j < blocks.length; j++) {
                char hash = blocks[j];
                buffer[index++] = (byte) (hash & 0xFF);
                buffer[index++] = (byte) (hash >> '\b' & 0xFF);
            }
        }

        // Write block light
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            byte[] blockLightBytes = section.getBlockLight().getHandle();
            append(blockLightBytes);
        }

        // Write sky light TODO: Check for overworld
        for(int i = 0; i < sectionCount; i++) {
            ChunkSection section = sections[i];
            byte[] skyLightBytes = section.getSkyLight().getHandle();
            append(skyLightBytes);
        }

        // Write biome info TODO: Check if not flat map
        byte[] biomeBytes = new byte[256];
        Arrays.fill(biomeBytes, (byte) 1); // PLAINS
        append(biomeBytes);

        if(index < buffer.length) {
            CubixServer.getLogger().log(Level.WARNING, "Remaining bytes after encoding chunk: " + (buffer.length - index));
        } else if(index > buffer.length) {
            // This should never happen due to out of bounds exception, but i check it anyway
            CubixServer.getLogger().log(Level.WARNING, "Chunk index has higher than the actual data size: "+ (index - buffer.length));
        }
    }

    // Append an array of bytes to the buffer
    private void append(byte[] bytes) {
        System.arraycopy(bytes, 0, buffer, index, bytes.length);
        index += bytes.length;
    }

    private int arrayLength(int bitSize, boolean biomeInfo, boolean skyLight) {
        int sectionLength = bitSize * 2 * 16 * 16 * 16; // sections * section width ^ 3 * 2 cause block id and block data
        int blockLightLength = bitSize * 16 * 16 * 16 / 2; // sections * section width ^ 3 / 2 cause nibble is half a byte
        int skyLightLength = skyLight ? bitSize * 16 * 16 * 16 / 2 : 0; // Only if overworld
        int biomeLength = biomeInfo ? 256 : 0; // Only if not a flat map
        return sectionLength + blockLightLength + skyLightLength + biomeLength;
    }
}
