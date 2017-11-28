package com.example.xieqe.test001.modeIterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by xieqe on 2017/11/16.
 */

public class Waitress {
    Menu breakfastMenu;
    Menu dinnerMenu;

    public void printMenu(){
        Iterator breakfastIterator = breakfastMenu.createIterator();
        Iterator dinnerIterator = dinnerMenu.createIterator();

        printMenu(breakfastIterator);
        printMenu(dinnerIterator);

    }

    public void printMenu(ArrayList<Menu> menus){
        for (Menu menu : menus){
            printMenu(menu.createIterator());
        }
    }

    private void printMenu(Iterator iterator){
        if (iterator.hasNext()){
            MenuItem item = (MenuItem) iterator.next();
            System.out.println(item.name);
        }
    }
}
