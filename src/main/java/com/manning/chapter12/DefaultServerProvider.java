package com.manning.chapter12;

import org.eclipse.jetty.npn.NextProtoNego;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Listing 12.1
 *
 * Author 卡卡
 * Created by jing on 2017/4/11.
 */
public class DefaultServerProvider implements NextProtoNego.ServerProvider {

    //定义所有的 ServerProvider 实现的协议
    private static final List<String> PROTOCOLS =
            Collections.unmodifiableList(Arrays.asList("spdy/2", "spdy/3", "http/1.1"));

    private String protocol;

    @Override
    public void unsupported() {
        protocol = "http/1.1";   //设置如果 SPDY 协议失败了就转到 http/1.1
    }

    @Override
    public List<String> protocols() {
        return PROTOCOLS;  //返回支持的协议的列表
    }

    @Override
    public void protocolSelected(String protocol) {
        this.protocol = protocol;  //设置选择的协议
    }

    public String getSelectedProtocol(){
        return protocol;
    }
}
