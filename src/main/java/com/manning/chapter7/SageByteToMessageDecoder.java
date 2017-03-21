package com.manning.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Listing 7.4
 * Author 卡卡
 * Created by jing on 2017/3/21.
 */
public class SageByteToMessageDecoder extends ByteToMessageDecoder {

    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readable = byteBuf.readableBytes();
        if (readable > MAX_FRAME_SIZE){
            byteBuf.skipBytes(readable);
            throw new TooLongFrameException("Frame too big!");
        }
    }
}
