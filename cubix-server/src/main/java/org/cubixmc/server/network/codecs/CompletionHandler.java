package org.cubixmc.server.network.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.cubixmc.server.network.Codec;

import java.util.List;

/**
 * Ensure that all data is complete and ready to be read or written.
 */
public class CompletionHandler extends ByteToMessageCodec<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        Codec.writeVarInt(byteBuf2, byteBuf.readableBytes());
        byteBuf2.writeBytes(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        if(byteBuf.readableBytes() <= 5) {
            // Check if length is written out yet
            while((byteBuf.readByte() & 0x80) != 0) {
                if(byteBuf.readableBytes() <= 0) {
                    byteBuf.resetReaderIndex();
                    return;
                }
            }

            byteBuf.resetReaderIndex();
        }

        int length = Codec.readVarInt(byteBuf, 5);
        if(byteBuf.readableBytes() < length) {
            // Packet not fully read yet
            byteBuf.resetReaderIndex();
            return;
        }

        // Read all info
        ByteBuf out = ctx.alloc().buffer(length);
        byteBuf.readBytes(out);
        list.add(out);
    }
}
