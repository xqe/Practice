package com.example.xieqe.test001.modelMethod;

/**
 * Created by xieqe on 2017/11/15.
 */

public class Coffee extends CaffeineBeverage{


    @Override
    void brew() {
        System.out.println("泡茶");
    }

    @Override
    void addCondiments() {
        System.out.println("加糖加奶");
    }

    @Override
    public boolean customerWantsCondiments() {
        return false;
    }
}
