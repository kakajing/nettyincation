package com.manning.chapter8;

import io.netty.channel.*;

import java.io.File;
import java.io.FileInputStream;

/**
 * Listing 8.11 使用FileReg传输文件内容
 * Author 卡卡
 * Created by jing on 2017/3/23.
 */
public class DefaultFileRegionUsage {
    public static void transfer(Channel channel, File file) throws Exception {
        FileInputStream in = new FileInputStream(file);
        //用于文件的完整长度
        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length());

        channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    Throwable cause = channelFuture.cause();
                }
            }
        });
    }
}
