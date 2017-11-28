package com.example.xieqe.test001.Array_Link;

import android.util.Log;

/**
 * Created by xieqe on 2017/6/19.
 */

public class Link {

    public long data;
    public Link next;
    public Link previous;

    public Link(long data){
        this.data = data;
    }

    public void displayLink(){
        Log.i("===", "displayLink: "+data);
    }

}
