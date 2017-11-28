package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/19.
 */

public class LinkQueue {

    private FirstLastList queue;

    public LinkQueue(){
        queue = new FirstLastList();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void insert(long data){
        queue.insertLast(data);
    }

    public long remove(){
        return queue.deleteFirst();
    }
}
