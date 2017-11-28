package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/21.
 */

public class NoMoneyState implements State {

    private static final String TAG = "NoMoneyState";

    private SaleMachine1 saleMachine1;

    public NoMoneyState(SaleMachine1 saleMachine){
        this.saleMachine1 = saleMachine;
    }

    @Override
    public void insertMoney() {
        saleMachine1.setState(saleMachine1.hasMoneyState);
        Log.i(TAG, "insertMoney: 投币成功");
    }

    @Override
    public void backMoney() {
        Log.i(TAG, "backMoney: 还没投钱呢");
    }

    @Override
    public void turnCrank() {
        Log.i(TAG, "backMoney: 还没投钱呢");
    }

    @Override
    public void dispense() {
        Log.i(TAG, "backMoney: 还没投钱呢");
    }
}
