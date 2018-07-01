package com.example.xieqe.test001.annotation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xqe on 2018/7/1.
 */

public class InjectManager {

    private static final String TAG = "InjectManager====";

    public static  void getAnnotationClass(Context context) {

    }

    /**注解View id*/
    public static void initInjectView(Object target) {
        Activity activity = (Activity) target;
        Class clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getAnnotations() != null) {
                    if (field.isAnnotationPresent(ViewInject.class)) {
                        field.setAccessible(true);//修改反射属性
                        ViewInject inject = field.getAnnotation(ViewInject.class);
                        field.set(activity,activity.findViewById(inject.value()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**注解点击监听 onClick*/
    public static void initInjectClick(final Activity activity) {
        Class clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            MyClick myClick = method.getAnnotation(MyClick.class);
            if ( myClick!= null) {
                method.setAccessible(true);
                int[] value = myClick.value();
                for (int j : value) {
                    int id = j;
                    final View btn = activity.findViewById(id);
                    btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity, btn);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    /**注解布局 layout id*/
    public static void intiInjectLayout(Object target) {
        Activity activity = (Activity) target;
        Class clazz = target.getClass();
        if (clazz.getAnnotations() != null) {
            if (clazz.isAnnotationPresent(LayoutInject.class)) {
                LayoutInject inject = (LayoutInject) clazz.getAnnotation(LayoutInject.class);
                activity.setContentView(inject.value());
            }
        }
    }

    public static void initUIPage(Activity activity) {

    }

    public static void initUIPage(Fragment fragment) {

    }

    public static void initUIPage(View view) {
    }
}
