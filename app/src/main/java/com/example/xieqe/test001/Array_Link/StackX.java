package com.example.xieqe.test001.Array_Link;

/**
 * Created by xieqe on 2017/6/16.
 */

public class StackX {

    private int maxSize;
    private char[] stackArray;
    private int top;

    public StackX(int maxSize){
        this.maxSize = maxSize;
        stackArray = new char[maxSize];
        top = -1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == maxSize-1;
    }

    public void push(char value){
        stackArray[++top] = value;
    }

    public long pop(){
        return stackArray[top--];
    }

    public long peek(){
        return stackArray[top];
    }

}
