package com.manning.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Listing 7.3
 * Author 卡卡
 * Created by jing on 2017/3/21.
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(String.valueOf(integer));
    }
}
