package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Listing 8.5  启用 HTTPS，只需添加 SslHandler
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean client;

    public HttpsCodecInitializer(SslContext context, boolean client) {
        this.context = context;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine sslEngine = context.newEngine(channel.alloc());
        pipeline.addFirst("ssl", new SslHandler(sslEngine));

        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        }else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }
}
