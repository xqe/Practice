package com.example.xieqe.test001.modelMethod;

/**
 * Created by xieqe on 2017/11/15.
 */

public class SortUtil {

    public static void sort(Object[] a){
        Object[] copy = a.clone();
        mergeSort(copy,a,0,a.length);
    }

    /**使用冒泡排序对数组排列，该方法可以看做一个模板方法，比较结果依赖于子类对compareTo的实现*/
    private static void mergeSort(Object src[], Object dest[],int low,int high){
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && ((Comparable)dest[j-i]).compareTo(dest[j]) > 0; j--){
                    swap(dest,j-1,j);
                }
            }
        }

    private static void swap(Object array[],int index1,int index2) {
        Object temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
