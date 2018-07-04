package com.example.xieqe.test001.pageListen;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PageWatcher {
    private static final String TAG = "PageWatcher===";

    private static MyInvocationHandler invocationHandler;

    public static void watchActivity(Activity activity) {
        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static void watchFragment(Fragment fragment) {
        //API 26之前 FragmentManager没有FragmentLifeCycleCallback，考虑动态代理方式
        MyInvocationHandler invocationHandler = new MyInvocationHandler(fragment);
        Proxy.newProxyInstance(fragment.getClass().getClassLoader(),new Class[]{Fragment.class},invocationHandler);


    }

    public static class MyInvocationHandler implements InvocationHandler {

        private Object target;

        public MyInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, "invoke: " + method.getName());
            Object object = method.invoke(target,args);
            return object;
        }
    }

    public static void watchView(View view) {
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }
}
