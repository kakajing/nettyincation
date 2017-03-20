package com.manning.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * Listing 6.6, 6.7 and 6.8
 * Author 卡卡
 * Created by jing on 2017/3/20.
 */
public class Writes {

    /**
     * Listing 6.6
     * @param context
     */
    public static void writeViaChannel(ChannelHandlerContext context){
        ChannelHandlerContext ctx = context;
        Channel channel = ctx.channel();
        channel.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

    /**
     * Listing 6.7
     * @param context
     */
    public static void writeViaChannelPipeline(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

    /**
     * Listing 6.8
     * @param context
     */
    public static void writeViaChannelHandlerContext(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }
}
