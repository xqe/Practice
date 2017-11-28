package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/21.
 */

public class SoldOutState implements State {

    private static final String TAG = "SoldState";
    SaleMachine1 saleMachine1;

    public SoldOutState(SaleMachine1 saleMachine) {
        this.saleMachine1 = saleMachine;
    }

    @Override
    public void insertMoney() {
        Log.i(TAG, "insertMoney: 卖光了，投了也没有用");
    }

    @Override
    public void backMoney() {
        Log.i(TAG, "backMoney: 还没投钱呢");
    }

    @Override
    public void turnCrank() {
        Log.i(TAG, "turnCrank: 卖光了");
    }

    @Override
    public void dispense() {
        Log.i(TAG, "dispense: 非法状态");
    }
}
