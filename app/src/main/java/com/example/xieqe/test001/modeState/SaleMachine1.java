package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/21.
 */

public class SaleMachine1 {
    private static final String TAG = "SaleMachine1";
    State noMoneyState;
    State hasMoneyState;
    State soldState;
    State soldOutState;
    State winnerState;
    State state = soldOutState;
    int goodsCount;

    public SaleMachine1(int goodsCount){
        this.goodsCount = goodsCount;
        noMoneyState = new NoMoneyState(this);
        hasMoneyState = new HasMoneyState(this);
        soldState = new SoldState(this);
        soldOutState = new SoldOutState(this);
        winnerState = new WinnerState(this);
        if (goodsCount > 0){
            state = noMoneyState;
        }
    }
    public void insertMoney(){
        state.insertMoney();
    }
    public void backMoney(){
        state.backMoney();
    }
    public void turnCrank(){
        state.turnCrank();
        state.dispense();
    }
    void setState(State state){
        this.state = state;
    }
    void releaseGoods(){
        Log.i(TAG, "releaseGoods: ");
        if (goodsCount != 0) {
            --goodsCount;
        }
    }
}
