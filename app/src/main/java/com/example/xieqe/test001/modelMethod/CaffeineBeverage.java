package com.example.xieqe.test001.modelMethod;

import java.util.Iterator;

/**
 * Created by xieqe on 2017/11/15.
 */

public abstract class CaffeineBeverage{
    /**准备饮料，按步骤进行*/
    final void prepareRecipe(){
        boilWater();
        brew();
        if (customerWantsCondiments()){
            addCondiments();
        }
        pourInCup();
    }

    /**冲泡*/
    abstract void brew();

    /**加调料*/
    abstract void addCondiments();

    /**烧水*/
    public void boilWater(){
        System.out.println("boil walter");
    }

    /**倒入杯中*/
    public void pourInCup(){
        System.out.println("pour into cup");
    }

    /**钩子*/
    public boolean customerWantsCondiments(){
        return true;
    }

}
