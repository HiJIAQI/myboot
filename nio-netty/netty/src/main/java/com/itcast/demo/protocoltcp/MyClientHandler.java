package com.itcast.demo.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String buffer = "测试TCP编解码!\n";
            ctx.writeAndFlush(buffer);
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
