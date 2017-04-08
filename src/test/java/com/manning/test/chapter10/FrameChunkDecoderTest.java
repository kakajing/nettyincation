package com.manning.test.chapter10;

import com.manning.chapter10.FrameChunkDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Listing 10.6
 *
 * Author 卡卡
 * Created by jing on 2017/4/8.
 */
public class FrameChunkDecoderTest {


    @Test
    public void testFramesDecoded(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
             buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        //写入 2 个字节并预测生产的新帧(消息)
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));

        try {
            //写一帧大于帧的最大容量 (3) 并检查一个 TooLongFrameException 异常
            channel.writeInbound(input.readBytes(4));
            //如果异常没有被捕获，测试将失败
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));

        Assert.assertTrue(channel.finish());

        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(2), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();

        buf.release();
    }
}
