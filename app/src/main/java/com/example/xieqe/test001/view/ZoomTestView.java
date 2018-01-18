package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by xqe on 2018/1/18.
 */

public class ZoomTestView extends ImageView {
    private static final String TAG = "ZoomTestView";

    public ZoomTestView(Context context) {
        super(context);
    }

    boolean isZoomed = false;
    float startX = 0;
    float startY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    float scaleDistanceX = Math.abs(event.getX(1) - event.getX(2));
                    float scaleDistanceY = Math.abs(event.getY(1) - event.getY(2));
                    Log.i(TAG, "onTouchEvent: scaleDistanceX:" + scaleDistanceX + ",scaleDistanceY" + scaleDistanceY);
                    if (scaleDistanceX != 0 || scaleDistanceY != 0) {
                        isZoomed = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                float moveDistanceX = event.getX() - startX;
                float moveDistanceY = event.getY() - startY;
                if (isZoomed) {
                    //处理移动
                } else {
                    //处理翻页

                }
                startX = 0;
                startY = 0;
                break;
            default:
        }


        return super.onTouchEvent(event);
    }
}
