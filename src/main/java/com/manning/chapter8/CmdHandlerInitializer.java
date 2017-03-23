package com.manning.chapter8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Listing 8.9 解码器的命令和处理程序
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //将提取 Cmd 对象和转发到在管道中的下一个处理器
        pipeline.addLast(new CmdDecoder(65 * 1024));
        //添加 CmdHandler 将接收和处理 Cmd 对象
        pipeline.addLast(new CmdHandler());

    }

    public static final class Cmd {
        private final ByteBuf name;
        private final ByteBuf aggs;

        public Cmd(ByteBuf name, ByteBuf aggs) {
            this.name = name;
            this.aggs = aggs;
        }

        public ByteBuf name(){
            return name;
        }

        public ByteBuf agges(){
            return aggs;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder{

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            //通过结束分隔从 ByteBuf 提取帧
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            if (frame == null){
                return null;
            }
            //找到第一个空字符的索引。首先是它的命令名；接下来是参数的顺序
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), (byte) ' ');
            //从帧先于索引以及它之后的片段中实例化一个新的 Cmd 对象
            return new Cmd(frame.slice(frame.readerIndex(), index),
                    frame.slice(index + 1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Cmd cmd) throws Exception {

        }
    }
}
