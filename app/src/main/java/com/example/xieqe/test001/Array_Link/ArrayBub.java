package com.example.xieqe.test001.Array_Link;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by xieqe on 2017/6/15.
 */

public class ArrayBub {

    private long[] array;
    private int elems;

    public ArrayBub(int max){
        array = new long[max];
        elems = 0;
    }

    public void insert(long value){
        array[elems] = value;
        elems++;
    }

    public void display(){
        for(int i = 0; i< elems; i++){
            Log.i("ArrayBub", "display: "+array[i]);
        }
    }

    /**冒泡排序*/
    public void bubbleSort(){
        int out,in;
        for(out = elems-1; out>1; out--){
            for(in = 0; in < out; in++){
                if(array[in] > array[in+1]){
                    swap(in,in+1);
                }
            }
        }
    }

    private void swap(int one,int two){
        long temp = array[one];
        array[one] = array[two];
        array[two] = temp;
    }

    /**选择排序*/
    public void selectSort(){
        int out,in,min;
        for(out = 0; out<elems-1;out++){
            min = out;
            for(in = out+1; in<elems; in++){
                if(array[in] < array[min]){
                    min = in;
                }
            }
            swap(out,min);
        }
    }


    /**插入排序*/
    public void insertSort(){
        int out,in;
        for(out = 1; out<elems; out++){
            long temp = array[out];
            in = out;
            while(in>0 && array[in-1] >= temp){
                array[in] = array[in-1];
                --in;
            }
            array[in] = temp;
        }
    }


    /**
     * 普通二分查找，根据value，返回对应的索引值
     * */
    public int find(long value){
        int lowerBound = 0;
        int upperBound = elems-1;
        int curIn;

        while (true){
            curIn = (lowerBound + upperBound)/2;
            if(array[curIn] == value){
                return curIn;
            }else if(lowerBound > upperBound){
                return elems;           //未找到
            }else {
                if(array[curIn] > value){//在上半区
                    upperBound = curIn - 1;
                }else{                  //下半区
                    lowerBound = curIn + 1;
                }
            }
        }
    }

    /**二分查找*/
    public static int rank(long key, long[] array){
        int low = 0;
        int high = array.length-1;
        int mid = low + (high-low)/2;
        while (low <= high){
            if (key < array[mid]){
                high = mid - 1;
            }else if (key > array[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 递归二分查找
     * 递归代替循环
     *
     * 递归：
     * 程序调用自身的编程技巧称为递归。
     * */
    public int find1(long value,int lowerBound, int upperBound){
        int curIn;
        curIn = (lowerBound + upperBound)/2;
        if(array[curIn] == value){
            return curIn;
        }else if(lowerBound > upperBound){
            return elems;
        }else{
            if(array[curIn] > value){
                return find1(value,lowerBound,curIn-1);
            }else{
                return find1(value,curIn+1,upperBound);
            }
        }
    }


    /**归并排序*/
    public void mergeSort(){
        long[] workSpace = new long[elems];
        recMergeSort(workSpace,0,elems-1);
    }

    private void recMergeSort(long[] workSpace, int lowerBound, int upperBound){
        if(lowerBound == upperBound){   //只有一个元素
            return;
        }else{
            int mid = (lowerBound + upperBound)/2;

            recMergeSort(workSpace,lowerBound, mid);
            recMergeSort(workSpace, mid+1, upperBound);
            merge(workSpace,lowerBound,mid+1,upperBound);
        }
    }

    private void merge(long[] workSpace, int lowPtr,int highPtr, int upperBound){
        int j = 0;
        int lowerBound = lowPtr;
        int mid = highPtr - 1;
        int n = upperBound - lowerBound + 1;

        while (lowPtr <= mid && highPtr <= upperBound){
            if(array[lowPtr] < array[highPtr]){
                workSpace[j++] = array[lowPtr++];
            }else{
                workSpace[j++] = array[highPtr++];
            }

            while(lowPtr <= mid){           //下半区已经装载完，但上半区没有
                workSpace[j++] = array[lowPtr++];
            }

            while (highPtr <= upperBound){  //上半区已经装载完，但下半区没有
                workSpace[j++] = array[highPtr++];
            }

            for (int i = 0; i < n; i++) {
                array[lowerBound+j] = workSpace[j]; //workSpace内容拷贝到array，从而将arry变成有序
            }
        }
    }


    /**希尔排序*/
    public void shellSort(){
        int inner,outer;
        long temp;

        int h = 1;
        while (h < elems/3){
            h = h*3+1;              //Knuth间隔序列计算公式，利用h生成有序的间隔数列   1,4,13,40,121,....
                                    //如elems = 10, 对应 h = 4;
        }

        while (h > 0){
                                    //h = 4
            for(outer = h; outer < elems; outer++){
                temp = array[outer];
                inner = outer;

                while (inner > h-1 && array[inner-h] >= temp){
                    array[inner] = array[inner - h];
                    inner -=h;     //比较下一个间隔上的数值,如比较8和4之后，8-4=4，继续比较4和0
                }
                array[inner] = temp;
            }
            h = (h-1)/3;          //不断缩小间隔，直到间隔变成1
        }
    }

    /**划分算法
     * 按筛选值将数组划分为上下两个半区
     * */
    public int partitionIt(int left,int right,long pivot){
        int leftPtr = left;
        int rightPtr = right;

        while (true){
            while(leftPtr < right && array[leftPtr] < pivot){
                leftPtr++;  //上半区遇到大于筛选值时跳出循环，否则继续右移比较
            }

            while (rightPtr > left && array[rightPtr] > pivot){
                rightPtr--; //下半区遇到小于筛选值时跳出循环，否则继续左移比较
            }

            if(leftPtr >= rightPtr){
                            //两个移动的索引值最终相遇，代表整个数组比较完成，跳出外层的while循环
                break;
            }else{
                            //内层的两个while循环结束时，代表分别找到了大于筛选值和小于筛选值的索引，
                            //因此交换二者到按筛选条件应该在的区域里去
                swap(leftPtr,rightPtr);
            }
        }
        Log.i("----", "partitionIt: -------"+leftPtr);
        display();
        return leftPtr;
    }

    /**快速排序，以最右边的值为枢纽划分
     * 快速排序基于划分算法+递归
     * */
    public void quickSort(){
        recQuickSort(0,elems-1);
    }

    private void recQuickSort(int left,int right){

        if(right - left <= 0){//已经完成排序
            return;
        }else{

            long pivot = array[right];
            int partition = partitionIt(left,right,pivot);
            recQuickSort(left,partition-1);
            recQuickSort(partition+1,right);
        }
    }























}
