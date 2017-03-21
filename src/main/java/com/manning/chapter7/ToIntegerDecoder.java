package com.manning.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Listing 7.1
 * Author 卡卡
 * Created by jing on 2017/3/21.
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() >= 4){
            //从入站 ByteBuf 读取 int ， 添加到解码消息的 List 中
            list.add(in.readInt());
        }
    }
}
