package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Listing 8.3 自动聚合
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        }else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
        pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));
    }
}
