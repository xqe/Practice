package com.example.xieqe.test001.Array_Link;


/**
 * Created by xieqe on 2017/6/20.
 */

public class ListIterator {

    private Link current;
    private Link previous;
    private LinkList ourList;

    public ListIterator(LinkList list){
        ourList = list;
    }

    /**从头开始，引用指向开头*/
    public void reset(){
        current = ourList.getFirst();
        previous = null;
    }

    public boolean atEnd(){
        return current.next == null;
    }

    public void nextLink(){
        current = current.next;
        previous = current;
    }

    public Link getCurrent(){
        return current;
    }

    public void insertAfter(long data){
        Link newLink = new Link(data);

        if(ourList.isEmpty()){
            ourList.setFirst(newLink);
            current = newLink;
        }else{
            //这里假设Link没有previous属性，不是双向链表使用的Link
            newLink.next = current.next;
            current.next = newLink;
            nextLink();     //插入后更新current
        }
    }

    /**
     * 在当前链结点之前插入一个数据链
     * */
    public void insertBefore(long data){
        Link newLink = new Link(data);

        if(previous == null){//队列开头
            newLink.next = ourList.getFirst();
            ourList.setFirst(newLink);
            reset();
        }else{
            newLink.next = previous.next;
            previous.next = newLink;
            current = newLink;
        }
    }

    public long deleteCurrent(){
        long value = current.data;
        if(previous == null){//队列开头
            ourList.setFirst(current.next);
            reset();
        }else{
            previous.next = current.next;
            if(atEnd()){  //如果引用指向队尾，current删除后重新指向开头，否则指向下一个
                reset();
            }else{
                current = current.next;
            }
        }
        return value;
    }
}
