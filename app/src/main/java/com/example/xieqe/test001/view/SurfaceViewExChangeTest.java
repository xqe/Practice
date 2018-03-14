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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqe on 2017/12/28.
 */

public class SurfaceViewExChangeTest extends FrameLayout {
    @BindView(R.id.button7)
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
        //lp1.setMargins(100,100,0,0);
        addView(surfaceView1);

        //lp2.setMargins(100,100,0,0);
        addView(surfaceView2);

        left1 = 200;
        top1 = 200;
        right1 = 900;
        bottom1 = 900;

        left2 = 200;
        top2 = 200;
        right2 = 600;
        bottom2 = 600;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isChanged) {
            Log.e("========", "onLayout: 蓝 大  黑小" );
            surfaceView1.layout(left1,top1,right1,bottom1);
            surfaceView2.layout(left2,top2,right2,bottom2);
        }else{

            Log.e("========", "onLayout: 蓝 小  黑大" );
            surfaceView1.layout(left2-200,top2,right2,bottom2);
            surfaceView2.layout(left1,top1,right1,bottom1);
        }
    }

    @OnClick(R.id.button7)
    public void onClick(){
        isChanged = !isChanged;
        if (!isChanged) {
            Log.e("========", "onLayout: 蓝 大  黑小" );
            surfaceView1.setZOrderOnTop(false);
            surfaceView1.getHolder().setFormat(PixelFormat.RGBX_8888);
            surfaceView2.setZOrderOnTop(true);
            surfaceView2.getHolder().setFormat(PixelFormat.TRANSPARENT);
        }else{

            Log.e("========", "onLayout: 蓝 小  黑大" );
            surfaceView2.setZOrderOnTop(false);
            surfaceView2.getHolder().setFormat(PixelFormat.RGBX_8888);
            surfaceView1.setZOrderOnTop(true);
            surfaceView1.getHolder().setFormat(PixelFormat.TRANSPARENT);
        }
        requestLayout();
    }
}
