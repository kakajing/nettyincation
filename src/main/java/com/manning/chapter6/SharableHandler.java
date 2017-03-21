package com.manning.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Listing 6.10 ChannelHandler 实现符合所有包含在多个管道的要求
 * Author 卡卡
 * Created by jing on 2017/3/21.
 */
@ChannelHandler.Sharable
public class SharableHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel read meassage " + msg);
        //日志方法调用， 并专递到下一个 ChannelHandler
        ctx.fireChannelRead(msg);
    }
}
