package com.manning.chapter5;

import io.netty.buffer.ByteBuf;

/**
 * Author 卡卡
 * Created by jing on 2017/2/23.
 */
public class ByteBufExamples {

    public static void heapBuffer(ByteBuf heapBuf){
        //查看ByteBuf是否有一个后备数组
        if (heapBuf.hasArray()){
            //如果是，获取这个数组的引用
            byte[] array = heapBuf.array();
            //计算第一个字节的偏移量
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            //获取可读字节长度
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    public static void directBuffer(ByteBuf directBuf){
        //查看ByteBuf是否有一个后备数组,如果确实没有，那么这是个direct buffer
        if (!directBuf.hasArray()){
            int lenght = directBuf.readableBytes();
            //分配一个新的数组来存放这个长度的字节
            byte[] array = new byte[lenght];
            //拷贝字节到这个数组
            directBuf.getBytes(directBuf.readerIndex(), array);
            handleArray(array, 0, lenght);
        }
    }

    private static void handleArray(byte[] array, int offset, int len){

    }

}
