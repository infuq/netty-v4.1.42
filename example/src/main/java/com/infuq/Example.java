package com.infuq;


import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledUnsafeDirectByteBuf;


public class Example {

    public static void main(String[] args) {

        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

        PooledUnsafeDirectByteBuf byteBuf = (PooledUnsafeDirectByteBuf) allocator.directBuffer(2048);

        long address = byteBuf.memoryAddress();

        System.out.println(Long.toHexString(address));

    }






}
