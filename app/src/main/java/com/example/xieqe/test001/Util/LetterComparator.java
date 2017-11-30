package com.example.xieqe.test001.Util;

import com.example.xieqe.test001.Bean.Person;

import java.util.Comparator;

/**
 * Created by xieqe on 2017/11/29.
 */

public class LetterComparator implements Comparator<Person> {
    @Override
    public int compare(Person lhs, Person rhs) {

        int lhsAscii = lhs.getPinyin().substring(0,1).toUpperCase().charAt(0);
        int rhsAscii = rhs.getPinyin().substring(0,1).toUpperCase().charAt(0);
        //不是字母
        if (PinyinUtil.isLetter(lhsAscii) && !PinyinUtil.isLetter(rhsAscii))
        {
            return -1;

        } else if (!PinyinUtil.isLetter(lhsAscii) && PinyinUtil.isLetter(rhsAscii))
        {
            return  1;

        } else if (!PinyinUtil.isLetter(lhsAscii) && !PinyinUtil.isLetter(rhsAscii))
        {
            return lhsAscii >= rhsAscii ? -1 : 1;
        }
        return lhs.getPinyin().compareTo(rhs.getPinyin());
    }





}
