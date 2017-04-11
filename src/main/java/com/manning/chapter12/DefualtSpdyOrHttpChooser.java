package com.manning.chapter12;

import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;

/**
 * 使用 Netty 的提供的抽象基类
 *
 * Author 卡卡
 * Created by jing on 2017/4/11.
 */
public class DefualtSpdyOrHttpChooser extends SpdyOrHttpChooser {

    protected DefualtSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }

    @Override
    protected SelectedProtocol getProtocol(SSLEngine engine) {

        DefaultServerProvider provider = (DefaultServerProvider) NextProtoNego.get(engine); //1
        String protocol = provider.getSelectedProtocol();

        if (protocol == null){
            return SelectedProtocol.UNKNOWN;  //2
        }
        switch (protocol){
            case "spdy/3.1":
                return SelectedProtocol.SPDY_3_1;  //3
            case "http/1.1":
                return SelectedProtocol.HTTP_1_1;  //4
            default:
                return SelectedProtocol.UNKNOWN;   //5
        }
    }

    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
        return new HttpRequestHandler();  //6
    }

    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
        return new SpdyRequestHandler();  //7
    }
}
