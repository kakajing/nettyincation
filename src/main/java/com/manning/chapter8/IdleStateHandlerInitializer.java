package com.manning.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 使用 IdleStateHandler 测试远端是否还活着，如果不是就关闭连接释放资源。
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));  //如果连接没有接收或发送数据超过60秒钟
        pipeline.addLast(new HeartBeatHandler());

    }

    public static final class HeartBeatHandler extends ChannelInboundHandlerAdapter {
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));  //心跳发送到远端

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                super.userEventTriggered(ctx, evt);
            }

        }
    }
}
