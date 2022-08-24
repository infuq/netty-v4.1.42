package com.infuq;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.nio.ch.DirectBuffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class Server {


    public static void main(String[] args) throws Exception {

        /*
        -Xms50M
        -Xmx50M
        -XX:MaxDirectMemorySize=32M
        -XX:MetaspaceSize=12M
        -XX:MaxMetaspaceSize=16M
        -XX:-UseCompressedClassPointers
        -XX:-UseCompressedOops

         */

        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        EventLoopGroup businessGroup = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        long heap = VM.current().addressOf(serverBootstrap);
//        System.out.println("heap address:\t 0x" + Long.toHexString(heap));


        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(30 * 1024 * 1024);
        ((DirectBuffer)byteBuffer).cleaner().clean();

//        System.out.println(ClassLayout.parseInstance(byteBuffer).toPrintable());

        try {

            serverBootstrap.group(bossGroup, workerGroup) // workerGroup会被 ServerBootstrapAcceptor 使用
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 会被 ServerBootstrapAcceptor 使用
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {  // 会被 ServerBootstrapAcceptor 使用
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ChannelPipeline channelPipeline = ch.pipeline();

                            channelPipeline.addLast(new StringEncoder());
                            channelPipeline.addLast(new StringDecoder());
                            channelPipeline.addLast("idleEventHandler", new IdleStateHandler(0, 0, 0));
                            channelPipeline.addLast(businessGroup, new ServerHandler());
                            channelPipeline.addAfter("idleEventHandler","loggingHandler",new LoggingHandler(LogLevel.INFO));


                        }
                    });

            ChannelFuture bindFuture = serverBootstrap.bind("127.0.0.1", 8080);

            bindFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("@(" + future.hashCode() + ")回调自定义的BindFuture");
                }
            });


            // bindFuture和channelFuture是同一个对象
            ChannelFuture channelFuture = bindFuture.sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }




    }









}
