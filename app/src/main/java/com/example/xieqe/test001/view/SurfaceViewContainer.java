package com.example.xieqe.test001.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import retrofit2.Retrofit;

/**
 * Created by xieqe on 2017/12/11.
 */

public class SurfaceViewContainer extends FrameLayout {
    private SurfaceView surfaceView;
    public SurfaceViewContainer(@NonNull Context context) {
        super(context);
        init();
    }

    public SurfaceViewContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        surfaceView = new SurfaceView(getContext());
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.i("===", "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i("===", "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i("===", "surfaceDestroyed: ");
            }
        });
        addView(surfaceView);
    }
}
