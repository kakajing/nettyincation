package com.manning.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Listing 10.3 AbsIntegerEncoder
 * Author 卡卡
 * Created by jing on 2017/3/30.
 */
public class AbsIntergerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4){
            //读取下一个输入 ByteBuf 产出的 int 值，并计算绝对值
            int value = Math.abs(byteBuf.readInt());
            list.add(value);
        }
    }
}
