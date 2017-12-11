package com.example.xieqe.test001.animation;

import android.view.animation.Interpolator;

import static java.lang.Math.PI;

/**
 * Created by xieqe on 2017/12/11.
 */

public class MenuInterpolator implements Interpolator {
    private float factor = 0.4f;

    public MenuInterpolator(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * PI) / factor) + 1);
    }
}
