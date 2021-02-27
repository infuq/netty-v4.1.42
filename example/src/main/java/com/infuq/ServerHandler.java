package com.infuq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private int count = 1;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("接收到客户端信息:" + msg);
        ctx.writeAndFlush("\r\nServer " + (count++) + " to Client...\r\n");

    }


}
