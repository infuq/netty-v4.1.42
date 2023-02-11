package com.infuq;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


public class Client {


    private Bootstrap bootstrap = new Bootstrap();

    private String serverIP = "127.0.0.1";
    private Integer port = 8081;


    public static void main(String[] args) {

        Client client = new Client();
        client.connect();

    }

    public void connect() {

        init();
        doConnect();
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }


    public void init() {

        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {

                        ChannelPipeline channelPipeline = ch.pipeline();

                    }
                });

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);


    }

    // 连接服务端
    public void doConnect() {

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(serverIP, port));

    }

}
