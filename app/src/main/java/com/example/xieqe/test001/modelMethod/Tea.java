package com.example.xieqe.test001.modelMethod;

import android.support.annotation.NonNull;

/**
 * Created by xieqe on 2017/11/15.
 */

public class Tea extends CaffeineBeverage implements Comparable<Tea>{

    @Override
    void brew() {
        System.out.println("冲咖啡");
    }

    @Override
    void addCondiments() {
        System.out.println("加柠檬");
    }

    @Override
    public int compareTo(Tea another) {
        return 0;
    }
}
