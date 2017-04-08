package com.manning.test.chapter10;

import com.manning.chapter10.AbsIntergerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author 卡卡
 * Created by jing on 2017/3/30.
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded(){

        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            //写入负整数
             buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntergerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf));

        Assert.assertTrue(channel.finish());
        for (int i = 0; i < 10; i++) {
            //读取产生到的消息，检查负值已经编码为绝对值
             Assert.assertEquals(i, channel.readOutbound());
        }
        Assert.assertNull(channel.readOutbound());
    }
}
