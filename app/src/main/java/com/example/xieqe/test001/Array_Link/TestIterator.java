package com.example.xieqe.test001.Array_Link;

import java.util.Iterator;

/**
 * Created by xieqe on 2017/6/26.
 */

public class TestIterator<T> implements Iterable<Integer>,Iterator<Integer> {

    private T just;
    private Integer[] array;
    private int n;

    public T getJust(){
        return just;
    }

    public void setJust(T just){
        this.just = just;
    }

    public TestIterator(int max){
        array = new Integer[max];
        n = max;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return n > 0;
    }

    @Override
    public Integer next() {
        return array[--n];
    }

    @Override
    public void remove() {
    }
}
