package com.example.xieqe.test001;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.LruCache;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xieqe.test001.Array_Link.ArrayBub;
import com.example.xieqe.test001.RxJava.Test;
import com.example.xieqe.test001.SQLite.OrderDao;
import com.example.xieqe.test001.aidl.Consumer;
import com.example.xieqe.test001.aidl.EventStorage;
import com.example.xieqe.test001.aidl.Producer;
import com.example.xieqe.test001.animation.SpringInterpolator;
import com.example.xieqe.test001.proxy.IOperate;
import com.example.xieqe.test001.proxy.MyInvocationHandler;
import com.example.xieqe.test001.proxy.OperateImpl;
import com.example.xieqe.test001.view.LetterView;
import com.example.xieqe.test001.view.RockerView;
import com.example.xieqe.test001.view.SPView;
import com.example.xieqe.test001.view.SurfaceViewContainer;
import com.example.xieqe.test001.view.SurfaceViewExChangeTest;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends Activity implements TestFragment.ParentListener {


    private final static String TAG = "MainActivity";
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.activity_main)
    FrameLayout contentView;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.line1)
    LinearLayout linearLayout;
    @Bind(R.id.spView)
    SPView spView;
    @Bind(R.id.letter_view)
    LetterView letterView;
    @Bind(R.id.surfaceViewContainer)
    SurfaceViewContainer surfaceViewContainer;
    @Bind(R.id.menuLayout)
    LinearLayout menuLayout;
    @Bind(R.id.rocker_view)
    RockerView rockerView;
    private Context context;

    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    Button floatButton;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        ViewGroup contentView = (ViewGroup) content.getChildAt(0);
        context = this;
        String methodName = this.getPackageName();
        Log.i("MainActivity", "onCreate: =====" + methodName);
        //huo获取到了焦点才会传递到这
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(TAG, "setOnKeyListener: ========" + keyCode);

                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:

                        Log.i(TAG, "setOnKeyListener: ========KEYCODE_DPAD_LEFT");
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:

                        Log.i(TAG, "setOnKeyListener: ========KEYCODE_DPAD_RIGHT");
                        return true;
                }
                return false;
            }
        });

        letterView.setSelectListener(new LetterView.SelectListener() {
            @Override
            public void onSelected(String letter) {
                ArrayList<String> arrayList1 = new ArrayList<>();
                Set<String> set = new HashSet<>();
                ArrayList<String> arrayList = new ArrayList<>(set);
            }
        });

        new Test().testRxJava();
        //contentView.addView(new SurfaceViewExChangeTest(this));
    }

    OrderDao dao;

    @Override
    protected void onResume() {
        super.onResume();
//        dao = new OrderDao(this);
//        dao.initTable();
        setLayoutAnimation();
    }

    @Override
    public void clickPosition(int position) {

        transactToFragment(new TestFragment2(), "fragment2");
    }

    @OnClick(R.id.button)
    public void onClick(View v) {
        //testMenuInterpolator();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300,100);
        //layoutParams.addRule(Gravity.CENTER);
        layoutParams.gravity = Gravity.CENTER;
        rockerView.setSize(layoutParams);
    }

    @OnClick(R.id.image)
    public void onImageViewClick(View v) {
        image.clearAnimation();
    }

    /*bubble sort start*/
    public void bubbleSortTest() {
        ArrayBub arrayBub = new ArrayBub(8);
        arrayBub.insert(6);
        arrayBub.insert(2);
        arrayBub.insert(5);
        arrayBub.insert(4);
        arrayBub.insert(1);
        arrayBub.insert(3);
        arrayBub.insert(8);
        arrayBub.insert(7);

        arrayBub.display();

        //arrayBub.bubbleSort();
        //arrayBub.selectSort();
        //arrayBub.insertSort();
        //arrayBub.shellSort();
        arrayBub.quickSort();
//        arrayBub.display();


        /*PriorityQ priorityQ = new PriorityQ(6);
        priorityQ.insert(2);
        priorityQ.display();
        priorityQ.insert(9);
        priorityQ.display();
        priorityQ.insert(4);
        priorityQ.display();
        priorityQ.insert(8);
        priorityQ.display();
        priorityQ.insert(5);
        priorityQ.display();*/
    }

    /*bubble sort end*/

    /*LruCache start*/
    private LruCache<String, Bitmap> memoryCache;

    public void lruCacheTest() {

        //初始化
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8; //缓存空间取内存的1/8
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //bitmap大小 = 每一像素行的字节数 * 高度
                int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                return size;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };

        //增删查操作
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ask);
        memoryCache.put("testKey", bitmap);           //存
        Bitmap bitmap1 = memoryCache.get("testKey");//取
        memoryCache.remove("testKey");              //删


    }
    /*LruCache end*/


    /*bitmap缩放测试 start*/
    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInsampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInsampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            //若宽高的一半仍大于目标宽高，就继续缩小两倍
            while ((halfWidth / inSampleSize) >= reqWidth
                    && (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }



    /*bitmap缩放测试 end*/

    /*AsyncTask test start*/
    private class MyAsyncTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... params) {
            publishProgress(50);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /*组合按键监听test start*/


    boolean isRightKeyDown = false;
    boolean isLeftKeyDown = false;
    boolean isMultiMode = false;
    Timer timer;
    long actionTime = 0;


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int action = event.getAction();

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && action == KeyEvent.ACTION_DOWN) {
            isRightKeyDown = true;
            keepMultiMode();
            if (actionTime == 0) {
                actionTime = System.currentTimeMillis();
            }
            return isMultiMode;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && action == KeyEvent.ACTION_UP) {
            isRightKeyDown = false;
            actionTime = 0;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && action == KeyEvent.ACTION_DOWN) {
            isLeftKeyDown = true;
            if (actionTime == 0) {
                actionTime = System.currentTimeMillis();
            }
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && action == KeyEvent.ACTION_UP) {
            isLeftKeyDown = false;
            actionTime = 0;
        }

        if (isRightKeyDown && isLeftKeyDown) {
            Log.i(TAG, "dispatchKeyEvent: ====");
        }


        return super.dispatchKeyEvent(event);
    }

    public void keepMultiMode() {
        isMultiMode = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isMultiMode = false;
            }
        }, 1000);
    }

    /*组合按键监听test end*/


    public class MyIntentService extends IntentService {

        public MyIntentService(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "onHandleIntent: " + action);
            SystemClock.sleep(3000);
            if (action.equals("start")) {
                Log.i(TAG, "onHandleIntent: task start");
            }
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "IntentService onDestroy");
            super.onDestroy();
        }
    }


    /*变长参数 test start*/
    public void test(int... numbers) {
        for (int number : numbers) {
            Log.i(TAG, "test: " + number);
        }
    }
    /*变长参数 test start*/


    /*ThreadLocal test start*/
    private ThreadLocal<Boolean> booleanThreadLocal = new ThreadLocal<>();

    private void threadLocalTest() {
        booleanThreadLocal.set(true);
        Log.i(TAG, "MainThread: " + booleanThreadLocal.get());

        new Thread() {
            @Override
            public void run() {
                super.run();
                booleanThreadLocal.set(false);
                Log.i(TAG, "thread1: " + booleanThreadLocal.get());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i(TAG, "thread2: " + booleanThreadLocal.get());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                Looper.loop();
            }
        }.start();
    }
    /*ThreadLocal test end*/


    /*WindowManager test start*/
    public void addViewByWindowManager() {
        floatButton = new Button(this);
        floatButton.setText("float button");
        floatButton.setBackground(getResources().getDrawable(R.drawable.btn_normal));
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.format = PixelFormat.RGBA_8888;//android 的一种32位颜色格式
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.x = 100;
        layoutParams.y = 300;
        layoutParams.width = 300;
        layoutParams.height = 300;
        /*WindowManager windowManager = this.getWindowManager();*/
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup viewGroup = (ViewGroup) floatButton.getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        windowManager.addView(floatButton, layoutParams);
        floatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) (event.getRawX() - floatButton.getWidth() / 2);
                        int y = (int) (event.getRawY() - floatButton.getHeight() / 2);
                        updateDrag(x, y);
                        break;
                }
                return true;
            }
        });
    }

    public void updateDrag(float x, float y) {
        layoutParams.alpha = 0.5f;
        layoutParams.x = (int) x;
        layoutParams.y = (int) y;
        windowManager.updateViewLayout(floatButton, layoutParams);
    }
    /*WindowManager test end*/


    /*RemoteViews test start*/
    public void createNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteviews_layout);
        remoteViews.setTextViewText(R.id.textView, "textView");
        remoteViews.setImageViewResource(R.id.imageView, R.drawable.btn_bg);

        Intent intent2 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 1, intent2, 0);
        remoteViews.setOnClickPendingIntent(R.id.start, pendingIntent2);


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setContentTitle("title")
                .setContentText("content text")
                .setSmallIcon(R.drawable.btn_bg)
                .setContent(remoteViews)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
    /*RemoteViews test end*/


    /*Animation test start*/
    public void viewAnimation_scale_test() {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.mix);
        button.startAnimation(animationSet);
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scalex_animation);
//        image.startAnimation(animation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void viewtranslation_animation_test() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.translation_animation);
        image.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void objectAnimator_test() {
        ObjectAnimator.ofInt(button, "width", 500).setDuration(2000).start();
    }

    public void testListenerChangeView() {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个估值对象，方便下面估值的时候使用
            private IntEvaluator evaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的进度值，整型，1-100之间
                int currentValue = (int) valueAnimator.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: " + currentValue);

                //获取当前进度占整个动画过程的比例，浮点型，0.0-1.0之间
                float fraction = animation.getAnimatedFraction();
                //使用整型估值器，根据动画执行百分比估算当前应该设置的宽度值
                //参数：1比例    2起始值    3结束值
                button.getLayoutParams().width = evaluator.evaluate(fraction, button.getWidth(), 500);
                button.requestLayout();
            }
        });

        valueAnimator.setDuration(2000).start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                button.setVisibility(View.GONE);\
                button.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void testAnimation() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(
                context, R.animator.single_animator);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setTarget(image);
        animator.setEvaluator(new MyIntEvaluator());
        animator.start();


        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(button, "translationX", -button.getHeight());
        objectAnimatorX.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimatorX.setRepeatCount(1);
        objectAnimatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimatorX.start();
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(button, "translationY", button.getWidth());
        objectAnimatorY.start();

        ValueAnimator valueAnimator = ObjectAnimator.ofInt(
                contentView, "backgroundColor",/*red*/0xffff8080,/*blue*/0xff8080ff);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();

        //以下动画顺序执行，每个动画时间5s
        animatorSet.playSequentially(
                ObjectAnimator.ofFloat(image, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(image, "rotationY", 0, 360),
                ObjectAnimator.ofFloat(image, "rotation", 0, -90),

                ObjectAnimator.ofFloat(image, "translationX", 0, 90),
                ObjectAnimator.ofFloat(image, "translationY", 0, 90),

                ObjectAnimator.ofFloat(image, "scaleX", 1, 1.5f, 1),
                ObjectAnimator.ofFloat(image, "scaleY", 1, 0.5f, 1),

                ObjectAnimator.ofFloat(image, "alpha", 1, 0.25f, 1)
        );

        //以下动画同时执行，动画时间5s
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(image, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(image, "rotationY", 0, 360),
                ObjectAnimator.ofFloat(image, "rotation", 0, -90),

                ObjectAnimator.ofFloat(image, "translationX", 0, 90),
                ObjectAnimator.ofFloat(image, "translationY", 0, 90),

                ObjectAnimator.ofFloat(image, "scaleX", 1, 1.5f, 1),
                ObjectAnimator.ofFloat(image, "scaleY", 1, 0.5f, 1),

                ObjectAnimator.ofFloat(image, "alpha", 1, 0.25f, 1)
        );
        animatorSet.setDuration(5 * 1000);
        //animatorSet.start();

        AnimatorSet animatorSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_test2);
        animatorSet1.setTarget(image);
        animatorSet1.setInterpolator(new LinearInterpolator());
        //animatorSet1.start();

    }
    /*Animation test end*/


    /*broadcastReceiver*/
    public void testBroadcastReceiver() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("")
                        .build();
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /*test proxy*/
    public void testProxy() {
        OperateImpl operateImpl = new OperateImpl();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(operateImpl);
        IOperate operate = (IOperate) Proxy.newProxyInstance
                (IOperate.class.getClassLoader(), new Class[]{IOperate.class}, invocationHandler);
        operate.operateMethod1("args add by user");
        operate.operateMethod2();
        operate.operateMethod3();


        EventStorage storage = new EventStorage();

        Producer producer = new Producer(storage);
        Thread thread1 = new Thread(producer);

        Consumer consumer = new Consumer(storage);
        Thread thread2 = new Thread(consumer);

        thread2.start();
        thread1.start();
    }

    /*test Annotation*/

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Inherited
    public @interface testAnnotation {

    }

    /*test fragment animation*/
    public void transactToFragment(Fragment fragment, String Tag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.animator.animator_test, R.animator.animator_test2);
        fragmentTransaction.replace(R.id.activity_main, fragment, Tag);
        fragmentTransaction.addToBackStack(Tag);
        fragmentTransaction.commit();
    }

    /*test layout animation*/
    public void setLayoutAnimation() {
        //方式一  代码加载写好的 layout animation 动画文件
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layoutanimation_test);
        linearLayout.setLayoutAnimation(layoutAnimationController);

        //方式二  代码初始化单个动画，然后创建一个新的LayoutAnimation —>layoutAnimationController
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scalex_animation);
        LayoutAnimationController layoutAnimationController1 = new LayoutAnimationController(animation);
        layoutAnimationController1.setDelay(0.5f);
        layoutAnimationController1.setOrder(LayoutAnimationController.ORDER_RANDOM);
        linearLayout.setLayoutAnimation(layoutAnimationController1);
    }

    public void testScheme() {
        Intent intent = new Intent();
        String uriStr = "xxx://goods:8888/goodDetail?goodId=10000";
        intent.setData(Uri.parse(uriStr));
        intent.setAction(Intent.ACTION_VIEW);

        if (judgeIntent(intent, this)) {
            startActivity(intent);
        } else {
            throw new ActivityNotFoundException("未找到");
        }
    }

    public static boolean judgeIntent(Intent intent, Context context) {

        PackageManager packageManager = context.getPackageManager();

        /**不含DEFAULT的Activity无法隐式启动，所以这里必须筛选含有DEFAULT的Activity*/
        List<ResolveInfo> results = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return !results.isEmpty();
    }

    public void testLoacalBroadcastManager() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xqe.testBroadcast");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "onReceive: ");
                Toast.makeText(context, "receive", Toast.LENGTH_LONG).show();
            }
        };
        //registerReceiver(receiver,intentFilter);
        //sendBroadcast(new Intent("com.xqe.testBroadcast"));
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, intentFilter);

        //可以在别的地方直接通过以下方式向在当前Activity注册的广播发送消息
        checkPermission();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.xqe.testBroadcast"));
    }

    /*permission test*/

    //需要配置的权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    //自定义请求权限返回码
    private static final int PERMISSION_REQUEST_CODE = 100;

    /**
     * 检查权限，只要有权限为被授予，就重新申请所有权限
     */
    public void checkPermission() {
        for (String permission : PERMISSIONS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int result = checkSelfPermission(permission);
                if (result == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
                    break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && isAllPermissionAllowed(grantResults)) {
            //所有权限已申请
        } else {
            //仍有权限未被授予，弹窗引导用户到设置—>权限，打开所需权限
        }
    }

    private boolean isAllPermissionAllowed(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public void testSpringRotateInterpolator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(image, "Rotation", 0, 360);
        animator.setDuration(2000);
        animator.setInterpolator(new SpringInterpolator(0.4f));
        animator.start();

        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(image, "ScaleX", 1.0f, 1.5f);
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(image, "ScaleY", 1.0f, 1.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new SpringInterpolator(0.4f));
        animatorSet.playTogether(scaleAnimatorX, scaleAnimatorY);
        animatorSet.start();
    }

    boolean isShow = true;

    public void testMenuInterpolator() {
        int offsetSeconds = 200;
        int startY, desY;
        for (int i = 0; i < menuLayout.getChildCount(); i++) {
            View view = menuLayout.getChildAt(i);

            if (isShow) {
                startY = 0;
                desY = view.getBottom();
            } else {
                startY = view.getBottom();
                desY = 0;
            }

            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "TranslationY", startY, desY);
            animator.setDuration(1000 + offsetSeconds * i);
            animator.setInterpolator(new SpringInterpolator(0.5f));
            animator.start();
        }
        //menuLayout.setVisibility(isShow ? View.GONE : View.VISIBLE);
        isShow = !isShow;
    }

    public String getString() {
        String s = "";
        return "";
    }
}
