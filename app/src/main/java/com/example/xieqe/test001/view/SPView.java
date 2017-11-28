package com.example.xieqe.test001.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2017/8/23.
 *
 */

public class SPView extends ViewGroup implements Runnable{

    private Paint paint;
    private float viewWidth;
    private float viewHeight;
    private float roundWidth;
    private float radius;
    private int currentSpeed;
    private int lastSpeed;
    private RectF oval;
    private RotateAnimation rotateAnimation;
    private View pointerView;
    private int marginLeft,marginRight,marginTop,marginBottom;

    public SPView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //setWillNotDraw(false);    导致2次绘制 2次onLayout
        setBackgroundColor(Color.TRANSPARENT);//导致1次绘制 2次onLayout
        //不添加背景 或 setWillNotDraw(false)  0次绘制，2次layout
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            addView(pointerView);

            // 系统自动测量子View: (适用于在布局文件中添加view的情况)
            //measureChild(pointerView, widthMeasureSpec, heightMeasureSpec);

            // 如果不希望系统自动测量子View,我们用以下的方式:
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec/2,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec/2,MeasureSpec.EXACTLY);
            pointerView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            Log.i("SPView", "onMeasure: "+pointerView.getMeasuredWidth()+"--"+pointerView.getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (!changed){
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
            marginLeft = marginLayoutParams.leftMargin;
            marginRight = marginLayoutParams.rightMargin;
            marginTop = marginLayoutParams.topMargin;
            marginBottom = marginLayoutParams.bottomMargin;

            //...根据margin值摆放child，即childView.layout时加上margin值或padding值
            //...
        }

        int childLeft = 0;
        int childTop = pointerView.getMeasuredHeight() / 2;
        int childRight = pointerView.getMeasuredWidth();
        int childBottom = pointerView.getMeasuredHeight() * 3 / 2;
        pointerView.layout(childLeft,childTop,childRight,childBottom);
    }

    private void init(){
        viewWidth = getResources().getDimension(R.dimen.xxhdpi_180);
        viewHeight = viewWidth;
        roundWidth = getResources().getDimension(R.dimen.xxhdpi_10);
        radius = (viewWidth/2 - roundWidth);
        paint = new Paint();
        oval = new RectF(roundWidth, roundWidth,viewWidth-roundWidth,viewWidth-roundWidth);
        pointerView = new ImageView(getContext());
        pointerView.setBackgroundResource(R.drawable.pointer);

        /*rotateAnimation = new RotateAnimation(0,-45,viewWidth/2,viewWidth/4);
        rotateAnimation.setFillAfter(true);
        pointerView.setAnimation(rotateAnimation);
        rotateAnimation.start();*/
        ObjectAnimator.ofFloat(pointerView,"rotation",-45f).start();
        ObjectAnimator.ofFloat(pointerView,"pivotX",viewWidth/2).start();
        ObjectAnimator.ofFloat(pointerView,"pivotY",viewWidth/4).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("SPView", "onDraw: ");
        drawArc(canvas);
        drawTick(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        new Thread(this).start();
        return super.onTouchEvent(event);
    }

    public void drawTick(Canvas canvas){

        //方式一：不改变圆心
        /*canvas.save();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        canvas.rotate(45,viewWidth/2,viewHeight/2); // 270°/ 60 = 4.5°
        for (int i = 0; i <= 60; i++) {
            canvas.drawLine(viewWidth/2,viewWidth*7/8,viewWidth/2,viewWidth-roundWidth,paint);
            canvas.rotate(4.5f,viewWidth/2,viewHeight/2);
        }
        canvas.restore();*/

        //方式二：将坐标原点移到圆心
        canvas.save();
        canvas.translate(viewWidth/2,viewHeight/2);
        paint.setAntiAlias(true);
        canvas.rotate(45); // 270°/ 60 = 4.5°
        for (int i = 0; i <= 60; i++) {
            if (i%10 == 0) {
                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(3);
                canvas.drawLine(0,radius*3/4,0,radius,paint);
            }else {
                paint.setStrokeWidth(2);
                paint.setColor(Color.GRAY);
                canvas.drawLine(0,radius*4/5,0,radius,paint);
            }
            canvas.rotate(4.5f);
        }
        canvas.restore();
    }

    public void drawArc(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);             //描边
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        //设置为true，画出的圆弧是个封闭图形，即两端加上了两条连接至圆心的线
        canvas.drawArc(oval,135,270,false,paint);
    }

    public void setSpeed(int speed){
        currentSpeed = speed * 270 /100;
        rotateAnimation = new RotateAnimation(lastSpeed,currentSpeed,viewWidth/2,viewWidth/4);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        pointerView.setAnimation(rotateAnimation);
        rotateAnimation.start();
        lastSpeed = currentSpeed;
        invalidate();
    }

    private boolean isRunning;

    @Override
    public void run() {
        while (isRunning){

            postInvalidate();
        }
    }
}
