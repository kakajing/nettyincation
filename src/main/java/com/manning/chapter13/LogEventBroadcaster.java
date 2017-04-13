package com.manning.chapter13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * Author 卡卡
 * Created by jing on 2017/4/13.
 */
public class LogEventBroadcaster {

    private final Bootstrap bootstrap;
    private final File file;
    private final EventLoopGroup group;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));  //1

        this.file = file;
    }

    public void run() throws Exception{
        Channel channel = bootstrap.bind(0).syncUninterruptibly().channel();//2
        System.out.println("LogEventBroadcaster running");
        long pointer = 0;
        for (;;){
            long length = file.length();
            if (length < pointer){
                pointer = length;   //3
            }else if (length > pointer){
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);   //4 设置当前文件的指针
                String line;
                while ((line = raf.readLine()) != null){
                    channel.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));  //5
                }
                pointer = raf.getFilePointer();  //6
                raf.close();
            }
            try {
                Thread.sleep(1000);  //7
            }catch (InterruptedException e){
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2){
            throw new IllegalArgumentException();
        }

       LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress(
               "255.255.255.255",
               Integer.parseInt(args[0])),
               new File(args[1]));   //8
        try {
            broadcaster.run();
        }finally {
            broadcaster.stop();
        }
    }
}
