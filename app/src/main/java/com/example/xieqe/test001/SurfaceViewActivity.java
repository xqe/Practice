package com.example.xieqe.test001;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SurfaceViewActivity extends Activity {

    @BindView(R.id.my_surfaceview)
    MySurfaceView mySurfaceview;
    @BindView(R.id.zoom_out)
    Button zoomOut;
    @BindView(R.id.zoom_in)
    Button zoomIn;
    private Unbinder unbinder;
    private int width;
    private int height;
    private int originWidth;
    private int originHeight;
    private int offset = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
        unbinder = ButterKnife.bind(this);
        getWH();
    }

    private void getWH() {
        mySurfaceview.post(new Runnable() {
            @Override
            public void run() {
                width = mySurfaceview.getWidth();
                height = mySurfaceview.getHeight();
                originHeight = height;
                originWidth = width;
            }
        });
    }

    @OnClick(R.id.zoom_in)
    public void zoomIn() {
        zoomOutWH();
        mySurfaceview.zoomIn();/*
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width,height);
        mySurfaceview.setLayoutParams(layoutParams);*/
    }

    @OnClick(R.id.zoom_out)
    public void zoomOut() {
        zoomInWH();
        mySurfaceview.zoomOut();/*
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width,height);
        mySurfaceview.setLayoutParams(layoutParams);*/
    }

    private void zoomInWH() {
        width -= 100;
        height -= 100;
        if (width <= 0) {
            width = 100;
        }

        if (height <= 0) {
            height = 100;
        }
    }

    private void zoomOutWH() {
        width += 100;
        height += 100;
        if (width > originWidth) {
            width = originWidth;
        }

        if (height > originHeight) {
            height = originHeight;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}