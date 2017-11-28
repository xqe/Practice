package com.example.xieqe.test001;

/**
 * Created by xieqe on 2017/9/8.
 */

public class StaticInnerSingleTon {

    public static int data;

    private StaticInnerSingleTon(){

    }

    public static StaticInnerSingleTon getInstance(){
        return Holder.instance;
    }

    private static class Holder{
        public static final StaticInnerSingleTon  instance = new StaticInnerSingleTon();
    }
}
