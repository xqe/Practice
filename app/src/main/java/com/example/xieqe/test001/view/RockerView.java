package com.example.xieqe.test001.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.xieqe.test001.R;

/**
 * Created by xqe on 2018/1/3.
 * 摇杆控制器 暂时只支持左右
 */

public class RockerView extends ViewGroup {

    private float touchX;
    private float touchY;
    private View dragView;
    private View arrowLeft;
    private View arrowRight;
    private int dragW;
    private int dragH;
    private int originX;
    private int originY;
    private boolean isSizeChange = true;

    private DragListener dragListener;

    public RockerView(Context context) {
        this(context,null);
    }

    public RockerView(Context context, AttributeSet attrs) {
        super(context, attrs);   init(attrs);
    }

    private void init(AttributeSet attrs) {
        // TODO: 从attrs中获取
        TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.RockerView);
        dragW = (int) array.getDimension(R.styleable.RockerView_dragViewW,150);
        dragH = (int) array.getDimension(R.styleable.RockerView_dragViewH,150);
        int dragViewResourceId = array.getResourceId(R.styleable.RockerView_dragViewBg,R.drawable.rocker_view_bg);

        dragView = new ImageView(getContext());
        arrowLeft = new ImageView(getContext());
        arrowRight = new ImageView(getContext());
        dragView.setBackgroundResource(dragViewResourceId);
        arrowLeft.setBackgroundResource(R.drawable.arrow_left);
        arrowRight.setBackgroundResource(R.drawable.arrow_right);

        setViewLayoutParams();

        array.recycle();
    }

    private void setViewLayoutParams() {
        dragView.setLayoutParams(new LayoutParams(dragW,dragH));
        arrowLeft.setLayoutParams(new LayoutParams(dragH * 3 / 15,dragH / 3));
        arrowRight.setLayoutParams(new LayoutParams(dragH * 3 / 15,dragH / 3));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("----", "onMeasure" + getMeasuredHeight());

        if (getChildCount() == 0) {
            //add arrow
            addView(arrowLeft);
            addView(arrowRight);
            addView(dragView);
        }
        if (isSizeChange) {
            originX = MeasureSpec.getSize(widthMeasureSpec) / 2;
            originY = MeasureSpec.getSize(heightMeasureSpec) / 2;
            touchX = originX;
            touchY = originY;
            isSizeChange = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        arrowLeft.layout(getMeasuredWidth() / 9,getMeasuredHeight() / 2 - dragH / 6,getMeasuredWidth() / 9 + dragH * 3 / 15,getMeasuredHeight() / 2 + dragH / 6);
        arrowRight.layout(getMeasuredWidth() * 8 / 9 - dragH * 3 / 15,getMeasuredHeight() / 2 - dragH / 6,getMeasuredWidth() * 8 / 9  ,getMeasuredHeight() / 2 + dragH / 6);
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

    public void setSize(LayoutParams layoutParams) {
        isSizeChange = true;
        setLayoutParams(layoutParams);
        dragW = layoutParams.height * 7 / 8;
        dragH = layoutParams.height * 7 / 8;
        setViewLayoutParams();
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
