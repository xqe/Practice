package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.xieqe.test001.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqe on 2017/12/28.
 */

public class SurfaceViewExChangeTest extends FrameLayout {
    @Bind(R.id.button7)
    Button button7;
    SurfaceView surfaceView1;
    SurfaceView surfaceView2;

    boolean isChanged;
    private int left1,top1,right1,bottom1;
    private int left2,top2,right2,bottom2;


    public SurfaceViewExChangeTest(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_surfaceview_test, this, true);
        ButterKnife.bind(this);

        surfaceView1 = new SurfaceView(getContext());
        surfaceView1.setBackgroundColor(Color.BLUE);
        surfaceView2 = new SurfaceView(getContext());
        surfaceView2.setBackgroundColor(Color.BLACK);
        LayoutParams lp1 = new LayoutParams(400,400);
        //lp1.setMargins(100,100,0,0);
        addView(surfaceView1,lp1);

        LayoutParams lp2 = new LayoutParams(200,200);
        //lp2.setMargins(100,100,0,0);
        addView(surfaceView2,lp2);

        surfaceView1.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.i("===1", "onLayoutChange: ==========right:" + right +",oldRight:" +oldRight);
            }
        });
        surfaceView2.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.i("===2", "onLayoutChange: ==========right:" + right +",oldRight:" +oldRight);
            }
        });
        left1 = 100;
        top1 = 100;
        right1 = 500;
        bottom1 = 500;

        left2 = 100;
        top2 = 100;
        right2 = 200;
        bottom2 = 200;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isChanged) {
            surfaceView1.layout(left1,top1,right1,bottom1);
            surfaceView2.layout(left2,top2,right2,bottom2);
            surfaceView2.setZOrderOnTop(true);
            surfaceView2.getHolder().setFormat(PixelFormat.TRANSPARENT);
        }else{
            surfaceView1.layout(left2,top2,right2,bottom2);
            surfaceView2.layout(left1,top1,right1,bottom1);
            surfaceView1.setZOrderOnTop(true);
            surfaceView1.getHolder().setFormat(PixelFormat.TRANSPARENT);
        }
    }

    @OnClick(R.id.button7)
    public void onClick(){
        isChanged = !isChanged;
        requestLayout();
    }
}
