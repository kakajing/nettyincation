package com.manning.test;

import com.manning.chapter10.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Listing 10.3 测试
 * Author 卡卡
 * Created by jing on 2017/3/30.
 */
public class FixedLengthFrameDecoderTest {


    @Test
    public void testFramesDecoder(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
             buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        
        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
             buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();

        System.out.println();
    }
}
