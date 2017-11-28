package com.example.xieqe.test001.modeIterator;

import java.util.Iterator;

/**
 * Created by xieqe on 2017/11/16.
 */

public class DinnerMenu extends Menu implements Iterator{
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;
    int position;

    public DinnerMenu(){
        menuItems = new MenuItem[MAX_ITEMS];
    }

    public void addItem(String name,int price){
        MenuItem item = new MenuItem(name,price);
        if (numberOfItems < MAX_ITEMS){
            menuItems[numberOfItems] = item;
            ++numberOfItems;
        }
    }

    public MenuItem[] getMenuItems(){
        return menuItems;
    }

    @Override
    public boolean hasNext() {
        if (position >= menuItems.length || menuItems[position] == null){
            return false;
        } else {
            return true;
        }
    }
    @Override
    public Object next() {
        MenuItem item = menuItems[position];
        ++position;
        return item;
    }

    @Override
    Iterator createIterator() {
        //Array没有自己的Iterator，需自己实现Iterator接口
        return this;
    }

    @Override
    public void remove() {
    }
}
