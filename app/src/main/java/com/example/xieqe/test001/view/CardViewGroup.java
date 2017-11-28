package com.example.xieqe.test001.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.xieqe.test001.animation.SpringInterpolator;

/**
 * Created by xieqe on 2017/9/18.
 *
 */

public class CardViewGroup extends ViewGroup {
    private final static String TAG = "===CardViewGroup";
    private Scroller scroller;
    private int currentIndex = 1;
    private int childWidth;
    private int childHeight;
    private int width,height;
    private int scaledTouchSlop;
    private float lastX;
    private VelocityTracker velocityTracker;
    private final static int SCROLL = 1;
    private final static int TOUCH = 1;
    private int touchMode;
    private View choiceView;
    private int choiceIndex;


    public CardViewGroup(Context context) {
        this(context,null);
    }

    public CardViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        scroller = new Scroller(getContext());
        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                lastX = x;
                touchMode = scroller.isFinished() ? TOUCH : SCROLL;
                break;
            case MotionEvent.ACTION_MOVE:
                int distance = (int) Math.abs(lastX - ev.getX());
                Log.i(TAG, "onInterceptTouchEvent: ACTION_MOVE"+distance);
                if (distance > scaledTouchSlop) {
                    Log.i(TAG, "onInterceptTouchEvent: ACTION_MOVE");
                    touchMode = SCROLL;
                }
                break;
            case MotionEvent.ACTION_UP:
                touchMode = TOUCH;
                break;
        }
        return touchMode == SCROLL;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        final float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: ACTION_DOWN");
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
               /* Log.i(TAG, "onTouchEvent: ACTION_MOVE:" + getScrollX());
                int distance = (int) (lastX - event.getX());
                lastX = x;
                scrollBy(distance,0);*/
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                float speedX = velocityTracker.getXVelocity();
                if (speedX > 600f){
                    //向左滑动速度满足
                    Log.i(TAG, "onTouchEvent: 向左滑动");
                    snapToIndex(currentIndex - 1);
                }else if (speedX < -600f) {
                    //向右滑动速度满足
                    Log.i(TAG, "onTouchEvent: 向右滑动");
                    snapToIndex(currentIndex + 1);
                }else {
                   /* //速度不满足，缓慢滑动，根据最终滑动距离判断滑动到哪一页
                    Log.i(TAG, "onTouchEvent: 缓慢滑动");
                    int index = (getScrollX() + childWidth/2)/childWidth;
                    snapToIndex(index);*/
                }
                velocityTracker.clear();
                velocityTracker.recycle();
                break;
        }
        return true;
    }

    public void snapToIndex(int index){
        Log.i(TAG, "snapToIndex: index:"+index );
        Log.i(TAG, "snapToIndex: left:"+getChildAt(0).getLeft() + ","+getChildAt(0).getRight());
        scroller.startScroll(currentIndex * childWidth,0,index * childWidth,0);
        currentIndex = index;
        requestLayout();
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            float offset = scroller.getCurrX()/scroller.getFinalX();
            if (currentIndex > choiceIndex){
                float scale = 1 + (childWidth + 20) * offset/childWidth;
                getChildAt(choiceIndex).setScaleX(scale);
            }
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        childWidth = MeasureSpec.getSize(widthMeasureSpec)/3;
        childHeight = MeasureSpec.getSize(heightMeasureSpec)*5/6;
        Log.i(TAG, "onMeasure: " + childWidth + "," +height + ","+childHeight);
        for (int i = 0; i < getChildCount(); i++){
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            getChildAt(i).measure(childWidthSpec,childHeightSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout: ");
        for (int i = 0; i < getChildCount(); i++){
            int left = l + i * childWidth;
            int right = left + childWidth;

            int centerPoint = getScrollX() + (i+1)*childWidth - childWidth/2;
            Log.i(TAG, "onLayout: " + left +"," +right);
            if (centerPoint == width/2) {
                //中心View
                Log.i(TAG, "onLayout:中心View：" + i);
                processCenterView(i);
                choiceView = getChildAt(i);
                choiceIndex = i;
            }else {
                getChildAt(i).layout(left, 10, right, height - 10);
            }
        }
    }

    private void processCenterView(int index){
        View view = getChildAt(index);
        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(view,"ScaleX",1.0f,1.2f);
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(view,"ScaleY",1.0f,1.2f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(100);
        animatorSet.playTogether(scaleAnimatorX,scaleAnimatorY);
        animatorSet.start();
        //view.setTranslationZ(1);
    }
}
