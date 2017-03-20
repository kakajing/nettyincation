package com.manning.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * Listing 6.5
 * Author 卡卡
 * Created by jing on 2017/3/20.
 */
public class ModifyChannelPipeline {

    public static void modifyPipeline() {
        ChannelPipeline pipeline = null;
        FirstHandle firstHandle = new FirstHandle();
        pipeline.addLast("handler1", firstHandle);
        pipeline.addLast("handler2", new SecondHandle());
        pipeline.addLast("handler3", new ThirdHandle());

        pipeline.remove("handler3");
        pipeline.remove(firstHandle);

        //将作为"handler2"的 SecondHandler 实例替换为作为 "handler4"的 FourthHandler
        pipeline.replace("handler2", "handler4", new ForthHandle());
    }

    private static final class FirstHandle extends ChannelHandlerAdapter{

    }

    private static final class SecondHandle extends ChannelHandlerAdapter{

    }

    private static final class ThirdHandle extends ChannelHandlerAdapter{

    }

    private static final class ForthHandle extends ChannelHandlerAdapter{

    }
}
