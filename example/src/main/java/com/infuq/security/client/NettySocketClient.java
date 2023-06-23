package com.infuq.security.client;

import com.infuq.security.server.ContextSSLFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;
import java.net.InetSocketAddress;
import java.net.SocketAddress;


public class NettySocketClient {

    public void connect(String ip, int port) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap strap = new Bootstrap();
            strap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            ChannelPipeline pipeline = socketChannel.pipeline();

                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("handler", new NettySocketSSLHandler());

                            SSLEngine engine = ContextSSLFactory.selectSslContextClient().createSSLEngine();
                            engine.setUseClientMode(true);
                            pipeline.addFirst("ssl", new SslHandler(engine));

                        }
                    });

            SocketAddress address = new InetSocketAddress(ip, port);
            final ChannelFuture future = strap.connect(address).sync();

            Channel channel = future.awaitUninterruptibly().channel();
            System.out.println("Connect server success, channel = " + channel.remoteAddress());

        } catch(Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new NettySocketClient().connect("127.0.0.1", 8081);
    }



}