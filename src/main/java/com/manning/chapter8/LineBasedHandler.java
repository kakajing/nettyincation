package com.manning.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Listing 8.8  Handling line-delimited frames
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class LineBasedHandler extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //提取帧并把数据包转发到下一个管道中的处理程序,在这种情况下就是 FrameHandler
        pipeline.addLast(new LineBasedFrameDecoder(65 * 1024));
        //接收帧
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override  //每次调用都需要传递一个单帧的内容
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        }
    }
}
