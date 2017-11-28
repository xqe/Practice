package com.example.xieqe.test001;

import android.animation.TypeEvaluator;

/**
 * Created by xieqe on 2017/8/25.
 */

public class MyIntEvaluator implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        //fraction代表时间流逝的百分比
        Float startInt = 200f;
        return (Float)(startInt + fraction * (endValue - startValue));
    }
}
