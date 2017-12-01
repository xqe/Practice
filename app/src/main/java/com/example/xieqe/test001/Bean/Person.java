package com.example.xieqe.test001.Bean;

import android.graphics.drawable.Drawable;

import com.example.xieqe.test001.Util.PinyinUtil;

/**
 * Created by xieqe on 2017/11/27.
 */

public class Person {

    private String name;
    private Drawable portrait;
    private String number;
    private String pinyin;

    public Person() {
    }

    public Person(String name, Drawable portrait, String number) {
        this.name = name;
        this.portrait = portrait;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getPortrait() {
        return portrait;
    }

    public void setPortrait(Drawable portrait) {
        this.portrait = portrait;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter(){
        if (PinyinUtil.isLetter(pinyin.charAt(0))){
            return pinyin.substring(0,1).toUpperCase();
        }else {
            return  "#";
        }
    }

    public static String[] DATA = {"张三","李四","王五","阿猫","阿狗","阿花","白白","123","哈哈","B哥","CC","*@d","012","张三","李四","王五","阿猫","阿狗","阿花","白白","123","都是","辅导费","让他让他","张三","李四",};
}
