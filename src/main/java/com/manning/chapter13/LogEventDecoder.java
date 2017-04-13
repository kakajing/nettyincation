package com.manning.chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2017/4/13.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf data = datagramPacket.content();  //1
        int i = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);//2 获取 SEPARATOR 的索引
        String filename = data.slice(0, i).toString(CharsetUtil.UTF_8);//3
        String logMsg = data.slice(i + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);  //4

        LogEvent event = new LogEvent(datagramPacket.recipient(),
                System.currentTimeMillis(),
                filename,
                logMsg);  //5
        out.add(event);

    }
}
