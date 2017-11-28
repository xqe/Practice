package com.example.xieqe.test001.proxy;

import android.util.Log;

/**
 * Created by xieqe on 2017/8/15.
 */

public class OperateImpl implements IOperate {
    
    private String TAG = "OprateImpl";
    
    @Override
    public void operateMethod1(String str) {
        Log.i(TAG, "operateMethod1: "+str);
    }

    @Override
    public void operateMethod2() {
        Log.i(TAG, "operateMethod2: ");
    }

    @Override
    public void operateMethod3() {
        Log.i(TAG, "operateMethod3: ");
    }
}
