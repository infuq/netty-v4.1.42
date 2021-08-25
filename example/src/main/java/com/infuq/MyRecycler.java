package com.infuq;

import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.io.IOException;

public class MyRecycler {

    private static final Recycler<Book> CHINESE = new Recycler<Book>() {
        @Override
        protected Book newObject(Handle<Book> handle) {
            return new Book(handle);
        }
    };

    private static final Recycler<Book> ENGLISH = new Recycler<Book>() {
        @Override
        protected Book newObject(Handle<Book> handle) {
            return new Book(handle);
        }
    };


    public static void main(String[] args) throws IOException {

        FastThreadLocalThread thread = new FastThreadLocalThread(() -> {

            CHINESE.get();

            ENGLISH.get();


            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();


        System.out.println("xxx");


    }



}
