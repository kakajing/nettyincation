package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Listing 8.1 SslHandler 使用 ChannelInitializer 添加到 ChannelPipeline
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        SSLEngine engine = context.newEngine(channel.alloc());
        channel.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
