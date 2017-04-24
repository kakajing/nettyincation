package com.manning.test.chapter14;

import com.manning.chapter14.MemcachedResponse;
import com.manning.chapter14.MemcachedResponseDecoder;
import com.manning.chapter14.Opcode;
import com.manning.chapter14.Status;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author 卡卡
 * Created by jing on 2017/4/24.
 */
public class MemcachedResponseDecoderTest {

    @Test
    public void testMemcachedResponseDecoder(){
        EmbeddedChannel channel = new EmbeddedChannel(new MemcachedResponseDecoder());  //1

        byte magic = 1;
        byte opCode = Opcode.SET;

        byte[] key = "Key1".getBytes(CharsetUtil.US_ASCII);
        byte[] body = "Value".getBytes(CharsetUtil.US_ASCII);
        int id = (int) System.currentTimeMillis();
        long cas = System.currentTimeMillis();

        ByteBuf buffer = Unpooled.buffer();  //2
        buffer.writeByte(magic);
        buffer.writeByte(opCode);
        buffer.writeShort(key.length);
        buffer.writeByte(0);
        buffer.writeByte(0);
        buffer.writeShort(Status.KEY_EXISTS);
        buffer.writeInt(body.length + key.length);
        buffer.writeInt(id);
        buffer.writeLong(cas);
        buffer.writeBytes(key);
        buffer.writeBytes(body);

        Assert.assertTrue(channel.writeInbound(buffer));   //3

        MemcachedResponse response = (MemcachedResponse) channel.readInbound();
        assertResponse(response, magic, opCode, Status.KEY_EXISTS, 0, 0, id, cas, key, body);

    }


    private static void assertResponse(MemcachedResponse response, byte magic, byte opCode, short status,
                                       int expires, int flages, int id, long cas, byte[] key, byte[] body){
        Assert.assertEquals(magic, response.magic());
        Assert.assertArrayEquals(key, response.key().getBytes(CharsetUtil.US_ASCII));
        Assert.assertEquals(opCode, response.opCode());
        Assert.assertEquals(status, response.status());
        Assert.assertEquals(cas, response.cas());
        Assert.assertEquals(expires, response.expires());
        Assert.assertEquals(flages, response.flags());
        Assert.assertEquals(body, response.data().getBytes(CharsetUtil.US_ASCII));
        Assert.assertEquals(id, response.id());
    }

}
