package com.example.xieqe.test001.ipc;

/**
 * Created by xieqe on 2017/8/16.
 */

public class Constant {

    public void method1(){
        synchronized (this){

        }
    }

    public synchronized void method2(){

    }

    public static void method3(){
        synchronized (Constant.class) {

        }
    }

    public static void method4(){
        Constant constant = new Constant();
        synchronized (constant.getClass()) {

        }
    }



    public static final int MSG_FROM_CLIENT = 1;
    public static final int MSG_FROM_SERVER = 2;
}
