package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/19.
 */

public class FirstLastList {

    private Link first;
    private Link last;

    public FirstLastList(){
        first = null;
        last = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insertLast(long data){
        Link newLink = new Link(data);
        if(isEmpty()){
            first = newLink;
        }else{
            last.next = newLink;
        }
        last = newLink;
    }

    public long deleteFirst(){
        long temp = first.data;
        if(first.next == null){
            last = null;
        }
        first = first.next;
        return temp;
    }
}
