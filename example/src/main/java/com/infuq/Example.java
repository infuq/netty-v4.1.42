package com.infuq;

import io.netty.buffer.PooledByteBufAllocator;

import java.util.HashMap;

public class Example {

    public static void main(String[] args) {


        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

        allocator.directBuffer(32 * 1024);//32K

        allocator.directBuffer(32 * 1024);//32K

//        allocator.directBuffer(2 * 1024);//2K

//        System.out.println(Integer.numberOfLeadingZeros(8192));

//                                  00000000000000000010000000000000


//        int arr[] = {1,2,3,4,5,6,7,8};
        // {1,2,3,4,5,6,7};

//        int arr[] = {1,2,4,5,6,7,8,9};
//        int arr[] = {1,2,3,4,5,6,7,9};
//
//        int value = arr[0] + arr[arr.length - 1];
//        int x = -1;
//        for (int i = 0; i < arr.length / 2; i++) {
//            if ( (arr[i] + arr[arr.length - 1 - i]) > value) {
//                x = arr[i] - 1;
//                break;
//            }
//            if ( (arr[i] + arr[arr.length - 1 - i]) < value) {
//                x = arr[arr.length - 1 - i] + 1;
//                break;
//            }
//        }
//
//        System.out.println(x);
        //   1 2 3 4 5 6 7 8 9
        //   1 2  3 4  5 6 7    9














    }





}
