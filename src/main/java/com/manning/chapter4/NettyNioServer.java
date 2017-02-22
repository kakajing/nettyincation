package com.manning.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Author 卡卡
 * Created by jing on 2017/2/22.
 */
public class NettyNioServer {

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi\r\n", Charset.forName("UTF-8")));
        //将NioEventLoopGroup用于非阻塞模式
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {  //指定每个新接受连接都会调用ChannelInitializer
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加一个ChannelInboundHandlerAdapter来解析和处理事件
                           ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                               @Override
                               public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                   //写数据到客户端并添加ChannelFutureListener，一旦消息写完关闭连接
                                   ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                               }
                           });
                        }
                    });
            //绑定服务器来接受新连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            //释放所以资源
            group.shutdownGracefully().sync();
        }
    }
}
