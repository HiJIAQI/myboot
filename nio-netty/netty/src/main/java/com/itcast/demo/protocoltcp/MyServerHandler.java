package com.itcast.demo.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

//处理业务的handler
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务器接收到数据 " + msg);
        System.out.println("服务器接收到消息量=" + (this.count++));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
