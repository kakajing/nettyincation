package com.manning.chapter14;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2017/4/21.
 */
public class MemcachedResponseDecoder extends ByteToMessageDecoder {

    private enum State{
        Header,
        Body
    }

    private State state = State.Header;
    private int totalBodySize;
    private byte magic;
    private byte opCode;
    private short keyLength;
    private byte extraLength;
    private short status;
    private int id;
    private long cas;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        switch (state){
            case Header:
                if (in.readableBytes() < 24){
                    return;  //如果不是至少24个字节是可读的,它不可能读整个头部,所以返回这里,等待再通知一次数据准备阅读
                }
                //读所有头的字段
                magic = in.readByte();
                opCode = in.readByte();
                keyLength = in.readShort();
                extraLength = in.readByte();
                in.skipBytes(1);
                status = in.readShort();
                totalBodySize = in.readInt();
                id  = in.readInt();
                cas = in.readLong();

                state = State.Body;
            case Body:
                if (in.readableBytes() < totalBodySize){
                    return;  //检查是否足够的数据是可读用来读取完整的响应的 body。长度是从头读取
                }
                int flags = 0, expires = 0;
                int actualBodySize = totalBodySize;
                //检查如果有任何额外的 flag 用于读
                if (extraLength > 0){
                    flags = in.readInt();
                    actualBodySize -= 4;
                }
                //检查如果响应包含一个 expire 字段，有就读它
                if (extraLength > 4){
                    expires = in.readInt();
                    actualBodySize -= 4;
                }
                String key = "";
                if (keyLength > 0){
                    ByteBuf keyBytes = in.readBytes(keyLength);
                    key = keyBytes.toString(CharsetUtil.UTF_8);
                    actualBodySize -= keyLength;
                }
                //读实际的 body 的 payload
                ByteBuf body = in.readBytes(actualBodySize);
                String data = body.toString(CharsetUtil.UTF_8);
                out.add(new MemcachedResponse(
                        magic,
                        opCode,
                        status,
                        id,
                        cas,
                        flags,
                        expires,
                        key,
                        data
                ));

                state = State.Header;
        }
    }
}
