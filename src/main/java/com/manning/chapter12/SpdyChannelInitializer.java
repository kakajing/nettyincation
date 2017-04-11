package com.manning.chapter12;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;

/**
 * Author 卡卡
 * Created by jing on 2017/4/11.
 */
public class SpdyChannelInitializer extends ChannelInitializer<SocketChannel>{

    private final SslContext context;

    public SpdyChannelInitializer(SslContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine engine = context.newEngine(channel.alloc());//3
        engine.setUseClientMode(false);   //4

        NextProtoNego.put(engine, new DefaultServerProvider());   //5
        NextProtoNego.debug  = true;

        pipeline.addLast("sslHandler", new SslHandler(engine));
        pipeline.addLast("chooser", new DefualtSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));

    }
}
