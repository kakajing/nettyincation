package com.manning.chapter14;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Author 卡卡
 * Created by jing on 2017/4/21.
 */
@ChannelHandler.Sharable
public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcachedRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MemcachedRequest msg, ByteBuf out) throws Exception {
        byte[] key = msg.key().getBytes(CharsetUtil.UTF_8);
        byte[] body = msg.body().getBytes(CharsetUtil.UTF_8);
        int bodySize = key.length + body.length + (msg.hasExtras() ? 8 : 0);

        out.writeByte(msg.magic());
        out.writeByte(msg.opCode());
        out.writeShort(key.length);

        int extraSize = msg.hasExtras() ? 0x08 : 0x0;
        out.writeByte(extraSize);

        //写数据类型,这总是0,因为目前不是在 Memcached,但可用于使用 后来的版本
        out.writeByte(0);
        //为保留字节写为 short ,后面的 Memcached 版本可能使用
        out.writeShort(0);
        out.writeInt(bodySize);

        out.writeLong(msg.cas());

        //编写额外的 flag 和到期时间为 int
        if (msg.hasExtras()){
            out.writeInt(msg.flages());
            out.writeInt(msg.expires());
        }

        out.writeBytes(key);
        out.writeBytes(body);
    }
}
