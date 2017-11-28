package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/19.
 */

public class LinkStack {

    private LinkList linkList;

    public LinkStack(){
        linkList = new LinkList();
    }

    public void push(long data){
        linkList.insertFirst(data);
    }

    public long pop(){
        return linkList.deleteFirst();
    }

    public boolean isEmpty(){
        return linkList.isEmpty();
    }
}
