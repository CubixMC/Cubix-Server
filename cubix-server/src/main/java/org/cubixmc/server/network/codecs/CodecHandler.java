package org.cubixmc.server.network.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.RequiredArgsConstructor;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.server.network.packets.PacketOut;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Encoding or decoding the packet and make it readable/writable by the software.
 */
@RequiredArgsConstructor
public class CodecHandler extends MessageToMessageCodec<ByteBuf, PacketOut> {
    private final Connection connection;

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketOut packet, List<Object> list) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        Codec codec = new Codec(out);

        // Write packet ID
        codec.writeVarInt(packet.getId());

        // Write packet data
        packet.encode(codec);
        list.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        Codec codec = new Codec(byteBuf);
        int id = codec.readVarInt();

        PacketIn packet = connection.getPhase().getPacket(id);
        if(packet == null) {
            throw new IOException("Unknown packet id: " + id);
        }

        packet.decode(codec);
        if(byteBuf.readableBytes() > 0) {
            throw new IOException("Remaining bytes after decoding packet " + id + ": " + byteBuf.readableBytes());
        }

        list.add(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        CubixServer.getLogger().log(Level.SEVERE, "Exception occurred in network codec during stage " + connection.getPhase(), cause);
    }
}
