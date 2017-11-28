package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieqe on 2017/8/31.
 *
 */

public class BezierView extends View {

    private Path path = new Path();
    private Paint paint = new Paint();
    private float tempX;
    private float tempY;
    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    public void touchDown(MotionEvent event){
        path.reset();
        tempX = event.getX();
        tempY = event.getX();
        path.moveTo(tempX,tempY);
    }

    /**以贝塞尔曲线形式画轨迹*/
    public void touchMove(MotionEvent event){
        float curX = event.getX();
        float curY = event.getY();

        float distanceX = Math.abs(curX - tempX);
        float distanceY = Math.abs(curY - tempY);

        if (distanceX > 3 || distanceY > 3){

            //贝塞尔曲线终点 = (当前触摸点 + 上次触摸点) / 2
            float endX = (curX + tempX)/2;
            float endY = (curY +tempY)/2;

            path.quadTo(tempX,tempY,endX,endY);

            //第二次画贝塞尔曲线，以上次的触摸点为控制点
            tempX = curX;
            tempY = curY;
        }
    }

    /**以直线形式画轨迹*/
    public void touchMove1(MotionEvent event){
        float curX = event.getX();
        float curY = event.getY();

        float distanceX = Math.abs(curX - tempX);
        float distanceY = Math.abs(curY - tempY);

        if (distanceX > 3 || distanceY > 3){

            path.lineTo(curX,curY);

            tempX = curX;
            tempY = curY;
        }
    }
}
