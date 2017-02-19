package com.manning.chapter1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listing 1.1 of <i>Netty in Action</i>
 * Author 卡卡
 * Created by jing on 2017/2/19.
 */
public abstract class BlockingIoExample {

    public void server(int portNumber) throws Exception{
        ServerSocket serverSocket = new ServerSocket(portNumber);
        //一直阻塞直到一个新的连接建立
        Socket clientSocket = serverSocket.accept();
        //从socket导出stream
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request, response;
        while ((request = in.readLine()) != null){
            //如果读到客户端“Done”，循环退出
            if ("Done".equals(request)){
                break;
            }
        }
        response = processRequest(request);
        out.println(response);
    }

    public static void main(String[] args) {

    }

    protected abstract String processRequest(String request);
}
