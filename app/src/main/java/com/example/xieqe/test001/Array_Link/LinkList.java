package com.example.xieqe.test001.Array_Link;


import android.util.Log;

/**
 * Created by xieqe on 2017/6/19.
 */

public class LinkList {
    private Link first;

    public LinkList(){
        first = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insertFirst(long data){
        Link newLink = new Link(data);
        newLink.next = first;
        first = newLink;
        //这里只是改变了引用指向的地址。所以对first重新赋值，相当于重新将first这个引用指向别的地址，不会改变newLink.next这个引用指向的地址
    }

    public long deleteFirst(){
        Link temp = first;
        first = first.next;
        return temp.data;
    }

    public void displayList(){
        Link current = first;
        while(current != null){
            Log.i("===", "displayList: "+first.data);
            current = current.next;
        }
        Log.i("===", "displayList: ");
    }

    public Link getFirst(){
        return first;
    }

    public void setFirst(Link first){
        this.first = first;
    }
}
