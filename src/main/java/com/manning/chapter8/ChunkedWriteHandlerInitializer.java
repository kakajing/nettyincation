package com.manning.chapter8;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Author 卡卡
 * Created by jing on 2017/3/23.
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

    private final File file;
    private final SslContext context;

    public ChunkedWriteHandlerInitializer(File file, SslContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //处理作为 ChunkedInput 传进的数据
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WriteStreamHandler());
    }

    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }


}
