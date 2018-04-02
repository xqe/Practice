package com.example.xieqe.test001;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static final String TAG = "MySurfaceView";
    private SurfaceHolder mHolder; // 用于控制SurfaceView
    private Thread drawThread; // 声明一条线程
    private boolean flag; // 线程运行的标识，用于控制线程
    private Canvas mCanvas; // 声明一张画布
    private Paint p; // 声明一支画笔
    private int x = 500;
    private int y = 500;
    private int r = 100; // 圆的半径
    private float scale = 1;

    public MySurfaceView(Context context) {
        this(context,null);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = getHolder(); // 获得SurfaceHolder对象
        mHolder.addCallback(this); // 为SurfaceView添加状态监听
        p = new Paint(); // 创建一个画笔对象
        p.setColor(Color.WHITE); // 设置画笔的颜色为白色
        setFocusable(true); // 设置焦点
    }

    /** 自定义一个方法，在画布上画一个圆*/
    public void doDraw() {
        Log.i(TAG, "doDraw: " + x + "," + y + ",scale:" + scale);
        mCanvas = mHolder.lockCanvas(); // 获得画布对象，开始对画布画画
        mCanvas.scale(scale,scale);
        mCanvas.drawRGB(0, 0, 0); // 把画布填充为黑色
        mCanvas.drawCircle(x, y, r, p); // 画一个圆
        mHolder.unlockCanvasAndPost(mCanvas); // 完成画画，把画布显示在屏幕上
    }

    public void zoomIn() {
        this.scale = scale + 0.1f;
        drawThread = new Thread(this); // 创建一个线程对象
        flag = true; // 把线程运行的标识设置成true
        drawThread.start(); // 启动线程
    }

    public void zoomOut() {
        this.scale = scale - 0.1f;
        drawThread = new Thread(this); // 创建一个线程对象
        flag = true; // 把线程运行的标识设置成true
        drawThread.start(); // 启动线程
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      /*  x = (int) event.getX(); // 获得屏幕被触摸时对应的X轴坐标
        y = (int) event.getY(); // 获得屏幕被触摸时对应的Y轴坐标
        Log.i(TAG, "onTouchEvent: ");*/
        return true;
    }

    @Override
    public void run() {
        while (flag) {
            doDraw(); // 调用自定义画画方法
            flag = false;
            try {
                Thread.sleep(2000); // 让线程休息50毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 当SurfaceView创建的时候，调用此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: ");
        drawThread = new Thread(this); // 创建一个线程对象
        flag = true; // 把线程运行的标识设置成true
        drawThread.start(); // 启动线程
    }

    /**
     * 当SurfaceView的视图发生改变的时候，调用此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * 当SurfaceView销毁的时候，调用此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}
