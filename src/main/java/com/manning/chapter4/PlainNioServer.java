package com.manning.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 不用Netty的非阻塞网络编程
 * Listing 4.2  of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/22.
 */
public class PlainNioServer {

    public void server(int port)throws Exception {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //设置成非阻塞
        socketChannel.configureBlocking(false);
        //讲服务器绑定到选定的端口
        ServerSocket ss = socketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);
        //打开Selector来处理channel
        Selector selector = Selector.open();
        //注册Selector到serverChannel来接受新的连接
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (;;){
            //等待处理新的事件，一直阻塞直到下一个事件到来
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            //获取所有收到事件的SelectionKey实例
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    //查看这个事件是否是一个等待接受的新连接请求
                    if (key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        //接受新的客户端注册到selector上
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("Accepted connection from " + client);
                    }
                    //查看这个socket是否已经准备好被写入数据
                    if (key.isWritable()){
                        SocketChannel clien = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()){
                            //写数据到这个连接的客户端
                            if (clien.write(buffer) == 0){
                                break;
                            }
                        }
                        clien.close();
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
