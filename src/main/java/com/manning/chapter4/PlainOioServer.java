package com.manning.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 不用netty的阻塞网络编程
 * Listing 4.1  of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/22.
 */
public class PlainOioServer {

    public void server(int port) throws Exception {
        final ServerSocket serverSocket = new ServerSocket(port);

        try {
            for (;;) {
                //接收一个连接
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                //创建一个新线程来处理这个连接
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            //写消息到这个已连接的客户端
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
