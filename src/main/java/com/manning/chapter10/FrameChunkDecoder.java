package com.manning.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Figure 10.4 如果输入字节超出限制长度就抛出TooLongFrameException，这样的功能一般用来防止资源耗尽
 *
 * Author 卡卡
 * Created by jing on 2017/4/8.
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int readableBytes = byteBuf.readableBytes();
        if (readableBytes > maxFrameSize){
            byteBuf.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = byteBuf.readBytes(readableBytes);
        list.add(buf);
    }
}
