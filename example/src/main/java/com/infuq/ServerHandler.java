package com.infuq;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private int count = 1;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {

        ChannelFuture channelFuture = ctx.writeAndFlush("\r\nServer " + (count++) + " to Client...\r\n");
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 执行此处的线程与执行channelRead0方法的线程是同一个线程
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
