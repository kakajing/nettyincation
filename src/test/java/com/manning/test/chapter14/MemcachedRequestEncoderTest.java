package com.manning.test.chapter14;

import com.manning.chapter14.MemcachedRequest;
import com.manning.chapter14.MemcachedRequestEncoder;
import com.manning.chapter14.Opcode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author 卡卡
 * Created by jing on 2017/4/24.
 */
public class MemcachedRequestEncoderTest {

    @Test
    public void testMemcachedRequestEncoder(){
        MemcachedRequest request = new MemcachedRequest(Opcode.SET, "key1", "value1");  //1

        EmbeddedChannel channel = new EmbeddedChannel(new MemcachedRequestEncoder());  //2
        channel.writeOutbound(request);  //3

        ByteBuf encoded = (ByteBuf) channel.readOutbound();

        Assert.assertNotNull(encoded);  //4
        Assert.assertEquals(request.magic(), encoded.readUnsignedByte());  //5
        Assert.assertEquals(request.opCode(), encoded.readByte());  //6
        Assert.assertEquals(4, encoded.readShort());  //7
        Assert.assertEquals((byte) 0x08, encoded.readByte());  //8
        Assert.assertEquals((byte) 0, encoded.readByte());  //9
        Assert.assertEquals(0, encoded.readShort());  //10
        Assert.assertEquals(4 + 6 + 8, encoded.readInt());  //11
        Assert.assertEquals(request.id(), encoded.readInt());  //12
        Assert.assertEquals(request.cas(), encoded.readLong());  //13
        Assert.assertEquals(request.flages(), encoded.readInt());  //14
        Assert.assertEquals(request.expires(), encoded.readInt());  //15

        byte[] data = new byte[encoded.readableBytes()];  //16
        encoded.readBytes(data);
        Assert.assertArrayEquals((request.key() + request.body()).getBytes(CharsetUtil.UTF_8), data);
        Assert.assertFalse(encoded.isReadable());  //17

        Assert.assertFalse(channel.finish());
        Assert.assertNull(channel.readInbound());
    }

}
