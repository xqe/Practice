package com.example.xieqe.test001.animation;

import android.view.animation.Interpolator;

/**
 * Created by xqe on 2018/1/3.
 */

public class DecelerateInterpolator implements Interpolator {
    private float factor = 1.0f;

    public DecelerateInterpolator(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        float result;
        if (factor == 1.0) {
            result = (float) (1.0 - (1.0 - input) * (1.0 - input));
        } else {
            result = (float) (1.0 - Math.pow((1.0 - input), 2 * factor));
        }
        return result;
    }
}
