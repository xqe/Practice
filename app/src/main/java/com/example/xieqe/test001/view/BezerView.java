package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieqe on 2017/11/24.
 */


public class BezerView extends View implements View.OnAttachStateChangeListener {

    private static final String TAG = "BezerView";
    private float screenWidth,screenHeight;
    private float controlX,controlY;
    private Paint paint;
    private Path path;

    public BezerView(Context context) {
        super(context);
        init();
    }

    public BezerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);

        path = new Path();
        this.addOnAttachStateChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                controlX = event.getX();
                controlY = event.getY();
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                controlX = screenWidth / 2;
                controlY = screenHeight / 5;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        path.reset(); //Clear any lines and curves from the path, making it empty.
        path.moveTo(screenWidth / 8,screenHeight / 5);
        path.quadTo(controlX,controlY,screenWidth * 7 / 8, screenHeight / 5);
        canvas.drawPath(path,paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(controlX,controlY,5,paint);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        Log.e(TAG, "onViewAttachedToWindow: " + v.getClass().getCanonicalName() );
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.e(TAG, "onViewDetachedFromWindow: " + v.getClass().getCanonicalName() );
    }
}
