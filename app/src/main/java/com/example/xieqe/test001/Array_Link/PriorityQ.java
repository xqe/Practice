package com.example.xieqe.test001.Array_Link;

import android.util.Log;

/**
 * Created by xieqe on 2017/6/19.
 * 优先级队列
 */

public class PriorityQ {

    private int maxSize;
    private long[] queArray;
    private int nItemts;

    public PriorityQ(int s){
        maxSize = s;
        queArray = new long[maxSize];
        nItemts = 0;
    }

    public void insert(long item){
        int j;

        if(nItemts == 0){
            queArray[nItemts++] = item;
        }else{
            for(j = nItemts-1;j>=0;j--){
                if(item > queArray[j]){
                    Log.i("===", "右移: j=="+j);//空出位置
                    queArray[j+1] = queArray[j];
                }else{
                    Log.i("===", "跳出循环: j=="+j);
                    break;
                }
            }
            Log.i("===", "insert: j=="+j);
            queArray[j+1] = item;
            nItemts++;
        }
    }

    public void display(){
        Log.i("===", "display: --------------------------------");
        for (int i = 0; i < nItemts; i++) {
            Log.i("===", "display: "+queArray[i]);
        }
    }

}
