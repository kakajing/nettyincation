package com.manning.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Listing 1.2 of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/19.
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter{

    //当一个新的连接建立时，channelActive(ChannelHandlerContext ctx) 被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client" + ctx.channel().remoteAddress() + " connected");
    }
}
