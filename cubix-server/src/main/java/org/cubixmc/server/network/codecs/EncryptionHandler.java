package org.cubixmc.server.network.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.cubixmc.server.CubixServer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.List;
import java.util.logging.Level;

/**
 * Encrypt/decrypt all packet data, this happens last when sending and first when reading
 */
public class EncryptionHandler extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private final Cipher encodeCipher;
    private final Cipher decodeCipher;

    public EncryptionHandler(SecretKey key) {
        this.encodeCipher = cipher(Cipher.ENCRYPT_MODE, key);
        this.decodeCipher = cipher(Cipher.DECRYPT_MODE, key);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuffer out = ByteBuffer.allocate(byteBuf.readableBytes());
        encodeCipher.update(byteBuf.nioBuffer(), out);
        out.flip();
        list.add(Unpooled.wrappedBuffer(out));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuffer out = ByteBuffer.allocate(byteBuf.readableBytes());
        decodeCipher.update(byteBuf.nioBuffer(), out);
        out.flip();
        list.add(Unpooled.wrappedBuffer(out));
    }

    private Cipher cipher(int type, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(type, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch(GeneralSecurityException e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to create cipher", e);
            return null;
        }
    }
}
