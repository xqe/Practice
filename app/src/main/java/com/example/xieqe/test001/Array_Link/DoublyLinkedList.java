package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/20.
 */

public class DoublyLinkedList {

    private Link first;
    private Link last;

    public DoublyLinkedList(){
        first = null;
        last = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insertFirst(long data){
        Link newLink = new Link(data);

        if(isEmpty()){
            last = newLink;
        }else{
            first.previous = newLink;
        }
        newLink.next = first;
        first = newLink;
    }

    public void insertLast(long data){
        Link newLink = new Link(data);

        if(isEmpty()){
            first = newLink;
        }else{
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
    }

    public Link deleteFirst(){
        Link temp = first;
        if(first.next == null){//只有一个
            last = null;        //先把last置空，只有一个的情况，后续会把first置空
        }else{
            first.next.previous = null;
        }
        first = first.next;
        return temp;
    }

    public Link deleteLast(){
        Link temp = last;
        if(first.next == null){//只有一个
            first = null;       //先把first置空，只有一个的情况，后续会把last置空
        }else{
            last.previous.next = null;
        }
        last = last.previous;
        return temp;
    }

    /**
     * @param currentData 数据插入到currentData后面
     * */
    public boolean insertAfter(long currentData,long data){
        Link current = first;
        while(current.data != currentData){
            current = current.next;
            if(current == null){
                return false;
            }
        }
        Link newLink = new Link(data);
        if(current == last){
            newLink.next = null;
            last = newLink;
        }else{
            current.next.previous = newLink;
            newLink.next = current.next;
        }
        newLink.previous = current;
        current.next = newLink;
        return true;
    }

    public Link deleteKey(long data){
        Link current = first;
        while (current.data != data){
            current = current.next;
            if(current == null){
                return null;
            }
        }
        if(current == first){
            first = current.next;
        }else{
            current.previous.next = current.next;
        }

        if(current == last){
            last = current.previous;
        }else {
            current.next.previous = current.previous;
        }
        return current;
    }
}
