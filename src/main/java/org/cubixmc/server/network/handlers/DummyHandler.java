package org.cubixmc.server.network.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;

@Sharable // Gives errors otherwise because used multiple times
public class DummyHandler extends ChannelHandlerAdapter {
    public static final DummyHandler INSTANCE = new DummyHandler();
}
