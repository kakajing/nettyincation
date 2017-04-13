package com.manning.chapter13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author 卡卡
 * Created by jing on 2017/4/13.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();  //2
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent logEvent) throws Exception {
        StringBuffer sb = new StringBuffer(); //3
        sb.append(logEvent.getReceivedTimestamp());
        sb.append(" [");
        sb.append("] [");
        sb.append(logEvent.getLogfile());
        sb.append("] : ");
        sb.append(logEvent.getMsg());

        System.out.println(sb.toString());  //4
    }
}
