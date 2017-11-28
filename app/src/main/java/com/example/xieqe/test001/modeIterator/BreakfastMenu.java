package com.example.xieqe.test001.modeIterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by xieqe on 2017/11/16.
 */

public class BreakfastMenu extends Menu{

    ArrayList<MenuItem> menuItems;
    int position = 0;

    public BreakfastMenu(){
        menuItems = new ArrayList<>();
        addItem("eggs",2);
        addItem("break",6);
    }

    public void addItem(String name,int price){
        MenuItem item = new MenuItem(name,price);
        menuItems.add(item);
    }

    public ArrayList<MenuItem> getMenuItems(){
        return menuItems;
    }

    @Override
    Iterator createIterator() {
        //ArrayList自带Iterator方法，不用重新创建
        return menuItems.iterator();
    }
}
