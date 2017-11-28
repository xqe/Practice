package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/19.
 */

public class SortedList {

    private Link first;

    public SortedList(){
        first = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insert(long data){
        Link newLink = new Link(data);
        Link previous = null;
        Link current = first;

        while(current != null && data > current.data){
            previous = current;
            current = current.next;
        }
        if(previous == null){
            first = newLink;
        }else{
            previous.next = newLink;
        }
        newLink.next = current;
    }
}
