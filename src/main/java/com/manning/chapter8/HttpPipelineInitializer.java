package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;

/**
 * Listing 8.2 支持 HTTP
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if (client) {
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpRequestDecoder());
        }else {
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpRequestDecoder());
        }
    }
}
