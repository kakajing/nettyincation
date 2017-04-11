package com.manning.chapter12;

import io.netty.channel.ChannelHandler;

/**
 * Author 卡卡
 * Created by jing on 2017/4/11.
 */
@ChannelHandler.Sharable
public class SpdyRequestHandler extends HttpRequestHandler {

    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n";
    }
}
