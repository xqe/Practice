package com.example.xieqe.test001.static_sort;

import android.util.Log;

/**
 * Created by xieqe on 2017/6/26.
 */

public class SortTest {

    private static final String TAG = "SortTest===";

    /**选择排序*/
    public static void selectionSort(long[] a){
        int N = a.length;
        for (int i = 0; i < N; i++){
            int min = i;    //最小元素索引
            for (int j = i+1; j < N; j++) {
                if (less(a[j],a[min])){
                    min = j;
                }
            }
            exchange(a,i,min);
        }
    }

    /** 插入排序*/
    public static void insertSort(long[] a){
        int N = a.length;
        for (int i = 1; i < N; i++){
            for (int j = i; j > 0 && less(a[j],a[j-1]); j--){
                exchange(a,j,j-1);
            }
        }
    }

    /**希尔排序*/
    public static void shellSort(long[] a){
        int N = a.length;
        int h = 1;
        while (h < N/3) {
            h = h*3 + 1;    //例N = 10，h = 4;
        }
        while (h >= 1){
            for (int i = h; i < N; i++) {
                //外层循环 i = 4、5、6、7、8、9
                //         i = 1、2、3、4、5、6、7、8、9

                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) { //插入排序方式
                    //内层循环比较 4-0、5-1、 6-2、 7-3、 8-4-0、 9-5-1
                    //             1-0、2-1-0、 3-2-1-0....
                    exchange(a,j,j-h);
                }
            }
            //缩小间隔 ，再次循环比较
            h = (h-1)/3;
        }
    }




    /**原地归并操作*/
    public static void merge(long[] a, int lo,int mid,int hi){
        int lowPtr = lo;
        int midPtr = mid+1;
        for (int k  = lo; k<= hi; k++){
            aux[k] = a[k];                  //将a[lo....hi]复制到aux[lo....hi]
        }

        for (int k = lo; k <= hi; k++){    //归并到a[lo....hi]

            if (lowPtr > mid){            //左半边用尽，取右半边元素

                a[k] = aux[midPtr++];

            }else if (midPtr > hi){      //右半边用尽，取左半边元素

                a[k] = aux[lowPtr++];

            }else if (less(aux[midPtr], aux[lowPtr])){
                                        //右半边的当前元素小于左半边的当前元素，取右半边元素
                a[k] = aux[midPtr++];

            }else {                     //左半边的当前元素小于右半边的当前元素，取左半边元素

                a[k] = aux[lowPtr++];

            }
        }
    }

    private static long aux[];

    public static void mergeSort(long[] a){
        aux = new long[a.length];
        mergeSortTop2Low(a, 0, a.length-1);
    }

    /**自顶向下归并排序*/
    private static void mergeSortTop2Low(long[] a, int lo, int hi){
        if (hi <= lo){  //只有一个元素的情况
            return;
        }
        int mid = lo + (hi - lo)/2;
        mergeSortTop2Low(a, lo, mid);
        mergeSortTop2Low(a, mid+1, hi);
        merge(a, lo, mid, hi);
    }

    /**自底向上归并排序*/
    private static void mergeSortLow2Top(long[] a){
        int N = a.length;
        for (int i = 1; i < N; i = i+i){
            for (int lo = 0; lo < N-i; lo += i+i){
                merge(a, lo, lo+i-1, Math.min(lo+i+i-1, N-1));
            }
        }
    }


    /**快速排序*/
    public static void quickSort(long[] a){
        recQuickSort(a, 0, a.length-1);
    }

    private static void recQuickSort(long[] a, int lo, int hi){
        if (hi <= lo){ //只有一个元素的情况
            return;
        }
        int j = partition(a, lo, hi);
        recQuickSort(a, lo, j-1);
        recQuickSort(a, j+1, hi);
    }

    /**快速排序*/
    public void sort(int[] a,int low,int high){
        int start = low;
        int end = high;
        int key = a[low];


        while(end>start){
            //从后往前比较
            while(end>start&&a[end]>=key) {
                //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            }
            if(a[end]<=key){
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while(end>start&&a[start]<=key) {
                //如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            }
            if(a[start]>=key){
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。
            // 左边的值都比关键值小，右边的值都比关键值大，
            // 但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if(start>low){
            //左边序列。第一个索引位置到关键值索引-1
            sort(a,low,start-1);
        }
        if(end<high) {
            //右边序列。从关键值索引+1到最后一个
            sort(a, end + 1, high);
        }
    }




    /**划分，以数组最左边的值为枢纽划分*/
    private static int partition(long[] a, int lo, int hi){
        int leftPtr = lo;
        int rightPtr = hi+1;
        long value = a[lo];
        while(true){//扫描左右，检查扫描是否到尽头，并交换元素

            //上半区leftPtr < 比较值时，代表leftPtr在正确区域，继续左移
            while (less(a[++leftPtr], value)){
                if (leftPtr == hi){
                    break;
                }
            }

            //下半区rightPtr > 比较值时，代表rightPtr在正确区域，继续左移
            while (less(value, a[--rightPtr])){
                if (rightPtr == lo){
                    break;
                }
            }

            //两个移动的索引值最终相遇，代表整个数组比较完成，跳出外层的while循环（唯一跳出循环条件）
            if (leftPtr >= rightPtr){
                break;
            }

            //内层的两个while循环结束时，代表分别找到了大于比较值和小于比较值的索引，
            //即a[leftPtr]  > a[rightPtr]， 因此交换二者
            exchange(a, leftPtr, rightPtr);

            //继续移动索引值进行比较，直到左右两指针相遇，表示整个数组比较完成
        }
        //外层循环结束，整个数组划分完成，最后rightPtr停留的位置就是比较值应该在的位置，交换
        exchange(a, lo, rightPtr);
        return rightPtr;
    }













    public static void test(){
        long[] a = {1,4,7,2,6,3,8,5,0,9};
        quickSort(a);
        for (int i = 0; i < a.length; i++) {
            Log.i(TAG, "test: "+a[i]);
        }
    }


    public static boolean less(long item1,long item2){
        return item1 < item2;
    }

    public static void exchange(long[] a,int index1,int index2){
        long temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

}
