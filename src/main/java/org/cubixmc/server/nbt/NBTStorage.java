package org.cubixmc.server.nbt;

import com.google.common.base.Charsets;
import org.cubixmc.server.CubixServer;

import java.io.*;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

public class NBTStorage {

    public static CompoundTag readCompound(File file) {
        try {
            return readCompound(new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file)))));
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to read compound tag", e);
            return null;
        }
    }

    public static CompoundTag readCompound(DataInput input) {
        try {
            // Read type
            NBTType type = NBTType.byTypeId(input.readByte());
            if(type == null || type != NBTType.COMPOUND) {
                throw new IllegalArgumentException("Can only read CompoundTag!");
            }

            // Read compound name
            String name = input.readUTF();

            // Read compound data
            CompoundTag compound = new CompoundTag(name);
            compound.decode(input);
            return compound;
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to read compound tag", e);
            return null;
        }
    }

    public static void writeCompound(CompoundTag compound, DataOutput output) {
        try {
            // Write type
            output.writeByte(compound.getType().getTypeId());

            // Write name
            output.writeShort(compound.getName().length());
            output.write(compound.getName().getBytes(Charsets.UTF_8));
//            output.writeUTF(compound.getName());

            // Write compound data
            compound.encode(output);
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to write compound tag", e);
        }
    }
}
