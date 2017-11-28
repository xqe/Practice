package com.example.xieqe.test001;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 自定义估值算法
 */
public class MyTypeEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        Point point = new Point();
        point.x = (int) (200 * (fraction * 1.5f));//fraction * 1.5f就是时间，fraction代表时间流逝的百分比，1.5代表1500ms即1.5s
        point.y = (int) (200 * (fraction * 1.5f) * (fraction * 1.5f));
        return point;
    }
}
