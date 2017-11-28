package com.example.xieqe.test001.animation;


import android.view.animation.Interpolator;

import static java.lang.Math.PI;

/**
 * Created by xieqe on 2017/9/19.
 */

public class SpringInterpolator implements Interpolator {

    private float factor;

    public SpringInterpolator(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        float result =
                (float)(Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * PI) / factor) + 1);
        return result;
    }
}
