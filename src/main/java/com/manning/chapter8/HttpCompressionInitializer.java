package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Listing 8.4 自动压缩HTTP消息
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpCompressionInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
            pipeline.addLast("decompressor", new HttpContentDecompressor());
        }else {
            pipeline.addLast("codec", new HttpServerCodec());
            pipeline.addLast("compressor", new HttpContentCompressor());

        }
    }
}
