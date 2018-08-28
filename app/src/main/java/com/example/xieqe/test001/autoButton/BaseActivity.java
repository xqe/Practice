package com.example.xieqe.test001.autoButton;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class BaseActivity extends Activity {
    private View rootView;
    private String viewTree;
    private static final String TAG = "BaseActivity";
    private long timeTag;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        rootView = getWindow().getDecorView();
        viewTree = getPackageName() + "." + getClass().getSimpleName();
        Log.e(TAG, "onAttachedToWindow: " + viewTree);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        int childCount = content.getChildCount();
        Log.e(TAG, "onStart: " + childCount + "---" + content.getChildAt(0).getClass().getSimpleName());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        timeTag = System.currentTimeMillis();
        ClickView clickView = new ClickView(rootView,viewTree);
        clickView = getClickView(clickView,ev,0);
        Log.e(TAG, "dispatchTouchEvent duration: " + (System.currentTimeMillis() - timeTag));
        if (clickView != null) {
            Log.e(TAG, "dispatchTouchEvent: " + getClass().getSimpleName());
            Log.e(TAG, "dispatchTouchEvent: " + clickView.getViewTree());
            Log.e(TAG, "dispatchTouchEvent: " + clickView.getView().getClass().getSimpleName());
            Log.e(TAG, "dispatchTouchEvent: " + clickView.getView().getId());
            Resources resources = getResources();
            Log.e(TAG, "dispatchTouchEvent: " + resources.getResourceName(clickView.getView().getId()));


        }
        return super.dispatchTouchEvent(ev);
    }

    private ClickView getClickView(ClickView clickView, MotionEvent event, int index) {
        Log.e(TAG, "getClickView" + index);
        View view = clickView.getView();
        if (event == null || view == null) {
            return null;
        }
        if (!isInView(view,event)) {
           return null;
        }
        if (view.getTag() != null) {
            //todo View添加TAG标记不上报
            Log.e(TAG, "getClickView view TAG:" + view.getTag());
        }
        String viewTree = clickView.getViewTree() + "." + view.getClass().getSimpleName() + "[" + index + "]";
        clickView.setViewTree(viewTree);
        if (view instanceof ViewGroup) {
             if (view instanceof AbsListView) {//排除listView
                 //todo listView如何定位
                return null;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            Log.e(TAG, "getClickView childCount:" + childCount);
            if (childCount != 0) {
                for (int i = 0; i < childCount; i++) {
                    //缩小范围定位到ViewGroup下的childView
                    ClickView childClickView = new ClickView(viewGroup.getChildAt(i),viewTree);
                    clickView = getClickView(childClickView,event,i);
                    if (clickView != null) {
                        break;
                    }
                }
            }
        }
        return clickView;
    }

    /**触摸点是否落在View上*/
    private boolean isInView(View view, MotionEvent event) {
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        }
        int touchX = (int) event.getRawX();
        int touchY = (int) event.getRawY();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        int viewW = view.getWidth();
        int viewH = view.getHeight();
        boolean isInX = touchX > viewX && touchX < viewX + viewW;
        boolean isInY = touchY > viewY && touchY < viewY + viewH;
        Log.e(TAG, "isInView x:" + touchX + "," + viewX + "," + viewW);
        Log.e(TAG, "isInView y:" + touchY + "," + viewY + "," + viewH);
        Log.e(TAG, "isInView:" + isInX + "," + isInY);
        return isInX && isInY;
    }
}
