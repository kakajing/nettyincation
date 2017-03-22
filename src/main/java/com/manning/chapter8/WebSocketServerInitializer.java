package com.manning.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Listing 8.6 在服务器上支持WebSocket
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new HttpServerCodec(),
                new HttpObjectAggregator(65536),  //用于提供在握手时聚合 HttpRequest
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());

    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {

        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {

        }
    }
}
