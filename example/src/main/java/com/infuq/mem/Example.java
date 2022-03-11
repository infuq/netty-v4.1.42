package com.infuq.mem;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class Example {


    public static void main(String[] args) {


        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;


        ByteBuf byteBuf1 = allocator.directBuffer(16 * 1024);
        ByteBuf byteBuf2 = allocator.directBuffer(16 * 1024);

        byteBuf1.release();



        System.out.println("stop...");



    }


}
