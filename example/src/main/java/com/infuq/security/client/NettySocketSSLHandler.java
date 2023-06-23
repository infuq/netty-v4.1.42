package com.infuq.security.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettySocketSSLHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettySocketSSLHandler#channelActive");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        ctx.fireChannelRead(msg);

        System.out.println("Client receive message:" + msg);

        ctx.channel().writeAndFlush("I'm Client. success read message");

    }


}
