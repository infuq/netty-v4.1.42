package com.infuq;

import io.netty.util.Recycler;

public class Book {



    private Recycler.Handle handle;

    public Book(Recycler.Handle handle) {
        this.handle = handle;
    }

}
