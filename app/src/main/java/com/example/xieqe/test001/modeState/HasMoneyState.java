package com.example.xieqe.test001.modeState;

import android.util.Log;

import java.util.Random;

/**
 * Created by xieqe on 2017/11/21.
 */

public class HasMoneyState implements State {

    private static final String TAG = "HasMoneyState";
    SaleMachine1 saleMachine1;
    Random winnerRandom = new Random();

    public HasMoneyState(SaleMachine1 saleMachine) {
        this.saleMachine1 = saleMachine;
    }

    @Override
    public void insertMoney() {
        Log.i(TAG, "insertMoney: 已经投过了");
    }

    @Override
    public void backMoney() {
        saleMachine1.setState(saleMachine1.noMoneyState);
        Log.i(TAG, "backMoney: 钱已退回");
    }

    @Override
    public void turnCrank() {
        //加入中奖模式
        int winner = winnerRandom.nextInt(10);//0-9的随机数，取0,10%的中奖率
        if (winner == 0 && saleMachine1.goodsCount > 1){
            saleMachine1.setState(saleMachine1.winnerState);
        }else {
            saleMachine1.setState(saleMachine1.soldState);
        }
        Log.i(TAG, "turnCrank:准备开始出货");
    }

    @Override
    public void dispense() {
        Log.i(TAG, "dispense: 非法状态");
    }
}
