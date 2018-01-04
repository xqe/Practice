package com.example.xieqe.test001.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xieqe.test001.R;
import com.example.xieqe.test001.animation.DecelerateInterpolator;
import com.example.xieqe.test001.animation.SpringInterpolator;

/**
 * Created by xqe on 2018/1/3.
 * 摇杆控制器 暂时只支持左右
 */

public class RockerView extends ViewGroup {

    private float touchX;
    private float touchY;
    private View dragView;
    private int dragW;
    private int dragH;
    private int originX;
    private int originY;
    private int dragViewResourceId;
    private DragListener dragListener;

    public RockerView(Context context) {
        this(context,null);
    }

    public RockerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // TODO: 从attrs中获取
        dragW = 150;
        dragH = 150;
        dragViewResourceId = R.mipmap.ic_launcher;


        dragView = new ImageView(getContext());
        dragView.setLayoutParams(new LayoutParams(dragW,dragH));
        dragView.setBackgroundResource(dragViewResourceId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            Log.i("----", "onMeasure");
            addView(dragView);
            originX = getMeasuredWidth() / 2;
            originY = getMeasuredHeight()/ 2;
            touchX = originX;
            touchY = originY;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = (int) (touchX - dragW / 2);
        int top = (int) (touchY - dragH / 2);
        int right = (int) (touchX + dragW / 2);
        int bottom = (int) (touchY + dragH / 2);
        Log.i("----", "onLayout: " + left + "," + top + "," + right + "," + bottom);
        dragView.layout(left,top,right,bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                touchY = event.getY();

                if (touchX > getMeasuredWidth() - dragW / 2) {
                    touchX = getMeasuredWidth() - dragW / 2;
                }
                if (touchX < dragW / 2) {
                    touchX = dragW / 2;
                }

                if (touchY > getMeasuredHeight() - dragH / 2) {
                    touchY = getMeasuredHeight() - dragH / 2;
                }
                if (touchY < dragH / 2) {
                    touchY = dragH / 2;
                }

                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                if (dragListener == null) {
                    Log.i("----", "onTouchEvent: touchX:" + touchX + ",originX:" + originX + ",dragW:" + dragW );
                    //while (isDraging)
                    {
                        if (touchX == getMeasuredWidth() - dragW / 2) {
                            Log.i("----", "onTouchEvent: dragRight");
                            //dragListener.dragRight();
                        } else if (touchX == dragW / 2) {
                            Log.i("----", "onTouchEvent: dragLeft");
                            //dragListener.dragLeft();
                        }
                    }
                }
                touchX = originX;
                touchY = originY;
                requestLayout();
                break;
            default:
                break;
        }


        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    public interface DragListener {
        void dragLeft();
        void dragRight();
    }


}
