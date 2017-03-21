package com.manning.chapter7;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Listing 7.10 将编码器和解码器组成一个编解码器
 * Author 卡卡
 * Created by jing on 2017/3/22.
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {

    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
