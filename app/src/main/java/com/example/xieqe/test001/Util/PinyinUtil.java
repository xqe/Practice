package com.example.xieqe.test001.Util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * Created by xieqe on 2017/11/29.
 */

public class PinyinUtil {


    public static String parseToPinyin(String str){
        char[] chars = str.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {

            if (String.valueOf(chars[i]).matches("[\u4e00-\u9fa5]")){ //汉字正则表达式
                try {
                    result.append(PinyinHelper.toHanyuPinyinStringArray(chars[i],format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                result.append(chars[i]);
            }
        }
        return result.toString();
    }

    public static boolean isLetter(int ascii){
        if (ascii >= 65 && ascii <= 90) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLetter(char ch){
        if (ch >= 65 && ch <= 90) {
            return true;
        } else {
            return false;
        }
    }

}
