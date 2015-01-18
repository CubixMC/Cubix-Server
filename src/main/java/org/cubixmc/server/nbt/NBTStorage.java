package org.cubixmc.server.nbt;

import com.google.common.base.Charsets;
import org.cubixmc.server.CubixServer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;

public class NBTStorage {

    public static CompoundTag readCompound(DataInput input) {
        try {
            // Read type
            NBTType type = NBTType.byTypeId(input.readByte());
            if(type == null || type != NBTType.COMPOUND) {
                throw new IllegalArgumentException("Can only read CompoundTag!");
            }

            // Read compound name
            int length = input.readInt();
            byte[] bytes = new byte[length];
            input.readFully(bytes);
            String name = new String(bytes, Charsets.UTF_8);

            // Read compound data
            CompoundTag compound = (CompoundTag) type.construct();
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

            // Write compound data
            compound.encode(output);
        } catch(IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to write compound tag", e);
        }
    }
}
