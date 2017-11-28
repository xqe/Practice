package com.example.xieqe.test001.modeState;

import android.util.Log;

/**
 * Created by xieqe on 2017/11/21.
 */

public class WinnerState implements State {

    private static final String TAG = "WinnerState";
    SaleMachine1 saleMachine1;

    public WinnerState(SaleMachine1 saleMachine) {
        this.saleMachine1 = saleMachine;
    }

    @Override
    public void insertMoney() {
        Log.i(TAG, "insertMoney: 错误信息");
    }

    @Override
    public void backMoney() {
        Log.i(TAG, "backMoney: 错误信息");
    }

    @Override
    public void turnCrank() {
        Log.i(TAG, "turnCrank: 错误信息");
    }

    @Override
    public void dispense() {
        Log.i(TAG, "dispense: 恭喜中奖，可以获得两个商品");
        saleMachine1.releaseGoods();
        if (saleMachine1.goodsCount == 0){
            saleMachine1.setState(saleMachine1.soldOutState);
        }else{
            saleMachine1.releaseGoods();
            if (saleMachine1.goodsCount > 0){
                saleMachine1.setState(saleMachine1.noMoneyState);
            }else {
                saleMachine1.setState(saleMachine1.soldOutState);
            }
        }
    }
}
