package com.manning.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Listing 4.5 and 4.6  of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/22.
 */
public class ChannelOperationExample {

    /**
     * Listing 4.5
     */
    public static void writingToChannel(){
        Channel channel = null;
        //创建ByteBuf来存放待写数据
        ByteBuf buf = Unpooled.copiedBuffer("you data", CharsetUtil.UTF_8);
        //写数据然后刷新
        final ChannelFuture future = channel.writeAndFlush(buf);

        //添加ChannelFutureListener以便在写操作完成后接收通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //写操作完成
                if (channelFuture.isSuccess()){
                    System.out.println("Write successful");
                }else {
                    System.out.println("Write error");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * Listing 4.6
     */
    public static void writingToChannelManyThreads(){
        final Channel channel = null;
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8).retain();
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(buf.duplicate());
            }
        };
        //获取线程池Executor引用
        Executor executor = Executors.newCachedThreadPool();

        //把写任务交给executor在一个线程中执行
        executor.execute(writer);
        //把另一个写任务交给executor在另一个线程中执行
        executor.execute(writer);
    }
}
