package com.manning.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Author 卡卡
 * Created by jing on 2017/3/23.
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        //提取基于帧编码长度8个字节的帧
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8));
        pipeline.addLast(new FrameHandler());  //处理每帧
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        }
    }
}
