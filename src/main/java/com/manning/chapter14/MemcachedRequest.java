package com.manning.chapter14;

import java.util.Random;

/**
 * Author 卡卡
 * Created by jing on 2017/4/21.
 */
public class MemcachedRequest {
    private static final Random rand = new Random();
    private final int magic = 0x80;//fixed so hard coded
    private final byte opCode; //the operation e.g. set or get
    private final String key; //the key to delete, get or set
    private final int flags = 0xdeadbeef; //random
    private final int expires; //0 = item never expires
    private final String body; //if opCode is set, the value
    private final int id = rand.nextInt(); //Opaque
    private final long cas = 0; //data version check...not used
    private final boolean hasExtras; //not all ops have extras

    public MemcachedRequest(byte opCode, String key, String value){
        this.opCode = opCode;
        this.key = key;
        this.body = value == null ? "" : value;
        this.expires = 0;
        hasExtras = opCode == Opcode.SET;
    }

    public MemcachedRequest(byte opCode, String key) {
       this(opCode, key, null);
    }

    public int magic(){  //幻数，它可以用来标记文件或者协议的格式
        return magic;
    }

    public int opCode(){  //反应了响应的操作已经创建了
        return opCode;
    }

    public String key(){
        return key;
    }

    public int flages(){
        return flags;
    }

    public int expires(){
        return expires;
    }

    public String body(){
        return body;
    }

    public int id(){
        return id;
    }

    public long cas(){  //compare-and-check 的值
        return cas;
    }

    public boolean hasExtras(){  // 如果有额外的使用，将返回 true
        return hasExtras;
    }
}
