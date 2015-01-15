package org.cubixmc.server.network.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;

import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RequiredArgsConstructor
public class CompressionHandler extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private final Connection connection;
    private final Deflater deflater = new Deflater();
    private final Inflater inflater = new Inflater();

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf prefix = ctx.alloc().buffer(5);
        int length = byteBuf.readableBytes();
        if(length >= connection.getCompression()) {
            int index = byteBuf.readerIndex();

            byte[] uncompressed = new byte[length];
            byteBuf.readBytes(uncompressed);
            deflater.setInput(uncompressed);
            deflater.finish();

            byte[] compressed = new byte[length];
            int compressedLength = deflater.deflate(compressed);
            deflater.reset();

            if(compressedLength == 0) {
                throw new IOException("Failed to compress packet!");
            } else if(compressedLength >= length) {
                // Compression is not efficient
                Codec.writeVarInt(prefix, 0);
                byteBuf.readerIndex(index);
                byteBuf.retain();
                list.add(Unpooled.wrappedBuffer(prefix, byteBuf));
            } else {
                Codec.writeVarInt(prefix, length);
                ByteBuf compressedData = Unpooled.wrappedBuffer(compressed, 0, compressedLength);
                list.add(Unpooled.wrappedBuffer(prefix, compressedData));
            }
        } else {
            Codec.writeVarInt(prefix, 0);
            byteBuf.retain();
            list.add(byteBuf);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int index = byteBuf.readerIndex();
        int decompressedLength = Codec.readVarInt(byteBuf, 32);
        if(decompressedLength == 0) {
            int length = byteBuf.readerIndex();
            if(length >= connection.getCompression()) {
                throw new IOException("Received uncompressed packet with a greater size than the threshold!");
            }

            ByteBuf out = ctx.alloc().buffer(length);
            byteBuf.readBytes(out);
            list.add(out);
        } else {
            byte[] compressed = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(compressed);
            inflater.setInput(compressed);

            byte[] uncompressed = new byte[decompressedLength];
            int decompressedSize = inflater.inflate(uncompressed);
            inflater.reset();

            if(decompressedSize == 0) {
                // Failed to compress, revert to index.
                byteBuf.readerIndex(index);
                byteBuf.retain();
                list.add(byteBuf);
            } else if(decompressedSize != decompressedLength) {
                throw new IOException("Uncompressed size doesn't match to the size of the decompressed data!");
            } else {
                list.add(Unpooled.wrappedBuffer(uncompressed));
            }
        }
    }
}
