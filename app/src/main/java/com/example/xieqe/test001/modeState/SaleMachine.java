package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/17.
 */

public class SaleMachine {

    private final static String TAG = "SaleMachine";
    private final static int SOLD_OUT = 0;
    private final static int SOLD = 1;
    private final static int NO_MONEY = 2;
    private final static int HAS_MONEY = 3;
    int state = SOLD_OUT;
    int count = 0;

    public SaleMachine(int count){
        this.count = count;
        if (count > 0) {
            state = NO_MONEY;
        }
    }
    /**投币*/
    public void insertMoney(){
        if (state == HAS_MONEY){
            Log.i(TAG, "insertMoney: 已投币");
        }else if (state == NO_MONEY){
            state = HAS_MONEY;
            Log.i(TAG, "insertMoney: 投入成功");
        }else if (state == SOLD_OUT){
            Log.i(TAG, "insertMoney: 卖光了，投了也没有用");
        }else if (state == SOLD){
            Log.i(TAG, "insertMoney: == 正在出货，出完再投");
        }
    }
    /**退钱*/
    public void backMoney(){
        if (state == HAS_MONEY){
            state = NO_MONEY;
            Log.i(TAG, "backMoney: 钱已退回");
        }else if (state == NO_MONEY){
            Log.i(TAG, "backMoney: 还没投钱呢");
        }else if (state == SOLD_OUT){
            Log.i(TAG, "backMoney: 还没投钱呢");
        }else if (state == SOLD){
            Log.i(TAG, "backMoney:已转动手柄开始出货，不能退回");
        }
    }

    /**转动手柄*/
    public void turnCrank(){
        if (state == HAS_MONEY){
            state = SOLD;
            dispense();
            Log.i(TAG, "turnCrank:准备开始出货");
        }else if (state == NO_MONEY){
            Log.i(TAG, "turnCrank: 还没投钱呢");
        }else if (state == SOLD_OUT){
            Log.i(TAG, "turnCrank: 卖光了");
        }else if (state == SOLD){
            Log.i(TAG, "turnCrank: 正在出货，转两次手柄也没有用");
        }
    }

    /**消费（出货）*/
    public void dispense(){
        if (state == HAS_MONEY){
            Log.i(TAG, "dispense: illegal state");
        }else if (state == NO_MONEY){
            Log.i(TAG, "dispense: need to pay first");
        }else if (state == SOLD_OUT){
            Log.i(TAG, "dispense: illegal state");
        }else if (state == SOLD){
            --count;
           if (count == 0){
               state = SOLD_OUT;
               Log.i(TAG, "dispense: out of goods");
           }else{
               state = NO_MONEY;
               Log.i(TAG, "dispense: get you goods");
           }
        }
    }
}
