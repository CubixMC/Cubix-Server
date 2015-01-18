package org.cubixmc.server.world;

import org.cubixmc.server.CubixServer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

public class RegionFile {
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

    public RegionFile(File file) {
        lock.readLock().lock();
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if(raf.length() < 8192L) {
                // Add extra data to ensure correct file size
                raf.seek(raf.length());
                long missing = 8192L - raf.length();
                for(int i = 0; i < missing; i++) {
                    raf.write(0);
                }
            }

            // Make file size dividable by 4096 bytes
            if(raf.length() % 4096L != 0) {
                long missing = raf.length() % 4096L;
                raf.seek(raf.length());
                byte[] missingBytes = new byte[(int) missing];
                raf.write(missingBytes);
            }

            raf.seek(0L);

            // A collection of writable locations
            int sectorLength = (int) raf.length() / 4096;
            this.reservedSectors = new BitSet(sectorLength);
            reservedSectors.set(0);
            reservedSectors.set(1);

            for(int i = 0; i < offsets.length; i++) {
                // Get offset for chunk
                int offset = raf.readInt();
                offsets[i] = offset;

                int position = offset >> 8; // The start position of the section
                int length = offset & 0xFF; // The length of the section
                if(offset != 0 && position + length <= sectorLength) {
                    for(int j = position; j < position + length; j++) {
                        reservedSectors.set(j);
                    }
                }
            }

            raf.close();
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to load region file", e);
        } finally {
            lock.readLock().unlock();
        }
    }
}
