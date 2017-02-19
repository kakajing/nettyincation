package com.manning.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Listing 1.3 and 1.4 of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/19.
 */
public class ConnectExample {

    public static void connect(Channel channel){

        //异步连接到远端
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
        //注册一个ChannelFutureListener用于在操作完成时收到通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    //如果成功，创建ByteBuf 来存放数据
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    //异步发送数据到远端，返回一个ChannelFuture
                    ChannelFuture wf = future.channel().writeAndFlush(buffer);
                }else {
                    //如果错误，读取描述错误原因的Throwable
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });

    }

}

