package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/21.
 */

public class SoldState implements State {

    private static final String TAG = "SoldState";
    SaleMachine1 saleMachine1;

    public SoldState(SaleMachine1 saleMachine) {
        this.saleMachine1 = saleMachine;
    }

    @Override
    public void insertMoney() {
        Log.i(TAG, "insertMoney:正在出货，出完再投");
    }

    @Override
    public void backMoney() {
        Log.i(TAG, "backMoney:已开始出货，不能退回");
    }

    @Override
    public void turnCrank() {
        Log.i(TAG, "turnCrank: 正在出货，转两次手柄也没有用");
    }

    @Override
    public void dispense() {
        saleMachine1.releaseGoods();
        Log.i(TAG, "dispense: 出货成功");
        if (saleMachine1.goodsCount > 0){
            saleMachine1.setState(saleMachine1.noMoneyState);
        }else {
            saleMachine1.setState(saleMachine1.soldOutState);
        }
    }
}
