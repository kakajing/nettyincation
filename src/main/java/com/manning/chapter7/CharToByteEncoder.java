package com.manning.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Listing 7.9 把字符转换为字节的编码器
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Character character, ByteBuf byteBuf) throws Exception {
        byteBuf.writeChar(character);
    }
}
