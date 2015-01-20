package org.cubixmc.server.world;

import org.cubixmc.server.CubixServer;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.NBTStorage;

import java.io.*;
import java.util.BitSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Wrapper for a region file (.mca) in minecraft
 * Format bytes:
 * 0    - 4096: Chunk offsets
 * 4096 - 8192: Chunk timestamps
 * 8192 - ????: Chunk sectors
 *
 * More sectors get added as it needs them along the way, to ensure storage efficiency.
 */
public class RegionFile {
    /**
     * The length of a chunk sector, 4KB
     */
    private static final int SECTOR_LENGTH = 4096;
    /**
     * A cached empty sector to write out
     */
    private static final byte[] EMPTRY_SECTOR = new byte[SECTOR_LENGTH];

    private final File file;
    /**
     * A lock to prevent writing while reading at the same time.
     * Yet it allows multiple readers.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * The offset locations for the chunks.
     */
    private final int[] offsets = new int[1024];
    /**
     * A collections of sectors that contain data, which we cannot put new chunks on.
     */
    private BitSet reservedSectors;
    /**
     * The total amount of sectors in the file (including header)
     */
    private int sectorCount;

    public RegionFile(File file) {
        this.file = file;
        if(!file.exists()) {
            try {
                // Create new region file
                file.createNewFile();
            } catch(IOException e) {
                CubixServer.getLogger().log(Level.SEVERE, "Failed to create new region file!", e);
            }
        }

        lock.writeLock().lock();
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            if(raf.length() < 8192L) {
                // Add extra data to ensure the header fits
                // 4KB for offsets, 4KB for timestamps
                raf.seek(raf.length());
                long missing = 8192L - raf.length();
                for(int i = 0; i < missing; i++) {
                    raf.write(0);
                }
            }

            // Make file size dividable by 4096 bytes
            if(raf.length() % SECTOR_LENGTH != 0) {
                long missing = raf.length() % SECTOR_LENGTH;
                raf.seek(raf.length());
                byte[] missingBytes = new byte[(int) missing];
                raf.write(missingBytes);
            }

            raf.seek(0L);

            // A collection of writable locations
            this.sectorCount = (int) raf.length() / 4096;
            this.reservedSectors = new BitSet(sectorCount);
            reservedSectors.set(0);
            reservedSectors.set(1);

            for(int i = 0; i < offsets.length; i++) {
                // Get offset for chunk
                int offset = raf.readInt();
                offsets[i] = offset;

                int position = offset >> 8; // The start position of the section
                int length = offset & 0xFF; // The length of the section
                if(offset != 0 && position + length <= sectorCount) {
                    for(int j = position; j < position + length; j++) {
                        reservedSectors.set(j);
                    }
                }
            }

            raf.close();
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to load region file", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public CompoundTag loadChunk(int x, int z) {
        int compression; // The compression type
        byte[] compressed; // The actual chunk data

        lock.readLock().lock();
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            // Look position up
            int hash = x + z * 32;
            long offset = offsets[hash];
            if(offset == 0) {
                // Offset not set, chunk not exist
                return null;
            }

            long dataPos = offset >> 8;
            long dataLength = offset & 0xFF;
            if(dataPos + dataLength > reservedSectors.size()) {
                // I don't know when this would happen
                return null;
            }

            raf.seek(dataPos * SECTOR_LENGTH);
            int length = raf.readInt();
            if(length > dataLength * SECTOR_LENGTH) {
                // This means that the length of the data is more than the reserved sectors? WTF!
                return null;
            }

            compression = raf.readByte();
            compressed = new byte[length - 1]; // Remove 1 from length because we read compression type already
            raf.readFully(compressed);

            raf.close();
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to load chunk data from file", e);
            return null;
        } finally {
            lock.readLock().unlock();
        }

        try {
            DataInputStream input;
            switch(compression) {
                case 1:
                    // Old compression type
                    input = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(compressed)));
                    break;
                case 2:
                    // Current compression type used by vanilla
                    input = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(compressed)));
                    break;
                default:
                    CubixServer.getLogger().log(Level.WARNING, "Unkown compression type: " + compression);
                    return null;
            }

            return NBTStorage.readCompound(input);
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to decompress chunk data from file", e);
            return null;
        }
    }

    public void saveChunk(int x, int z, CompoundTag compound) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8096);
        NBTStorage.writeCompound(compound, new DataOutputStream(new DeflaterOutputStream(baos)));
        byte[] compressed = baos.toByteArray();

        lock.writeLock().lock();
        try {
            int hash = x + z * 32;
            int offset = offsets[hash];
            int pos = offset >> 8; // The sector data offset
            int length = offset & 0xFF; // The amount of sectors allocated

            // Calculate the amount of sectors we need to store the new data
            int sectorsNeeded = (compressed.length + 5) / SECTOR_LENGTH + 1;
            if(sectorsNeeded >= 256) {
                // Chunks can not be > 1MB
                return;
            }

            RandomAccessFile raf = new RandomAccessFile(file, "w");
            if(length != 0 && length == sectorsNeeded) {
                // Write new timestamp
                raf.seek(SECTOR_LENGTH + hash * 4);
                raf.writeInt((int) (System.currentTimeMillis() / 1000L));

                // Write chunk data
                raf.seek(pos * SECTOR_LENGTH);
                raf.writeInt(compressed.length + 1);
                raf.writeByte(2);
                raf.write(compressed);
            } else {
                // We either lack section or we have too many, so lets remove our current ones.
                for(int i = 0; i < length; i++) {
                    reservedSectors.set(pos + i, false);
                }

                int start = findFreeSectors(sectorsNeeded);
                int end = start + sectorsNeeded;
                if(end >= sectorCount) {
                    // File is too small, grow the file.
                    raf.seek(raf.length());
                    for(int i = start; i < (start + end); i++) {
                        this.sectorCount += 1;
                        raf.write(EMPTRY_SECTOR);
                    }
                }

                // Reserve our sectors
                for(int i = start; i < (start + end); i++) {
                    reservedSectors.set(i);
                }

                // Update offsets & timestamp
                offsets[hash] = (start << 8) + sectorsNeeded;
                raf.seek(SECTOR_LENGTH + hash * 4);
                raf.writeInt((int) (System.currentTimeMillis() / 1000L));

                // Write the data
                raf.seek(start * SECTOR_LENGTH);
                raf.writeInt(compressed.length + 1);
                raf.writeByte(2);
                raf.write(compressed, 0, compressed.length);
            }

            raf.close();
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to save chunk data to file", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Find an offset for free sectors large enough to fit the chunk.
     *
     * @param length Amount of chunk sectors
     * @return A data offset for the chunk sectors
     */
    private int findFreeSectors(int length) {
        int index = 0;
        int found = 0;
        while(true) {
            if(!reservedSectors.get(index++)) {
                found += 1;
                if(found >= length) {
                    break;
                }
            } else {
                found = 0;
            }
        }

        return index;
    }
}
