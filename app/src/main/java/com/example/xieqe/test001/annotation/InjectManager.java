package com.example.xieqe.test001.annotation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by xqe on 2018/7/1.
 */

public class InjectManager {

    private static final String TAG = "InjectManager====";

    public static  void getAnnotationClass(Context context) {

    }

    public static List<String > getClassName(Context context){
        String packageName = "com.example.xieqe.test00";
        Log.e(TAG, "getClassName: " + packageName);
        List<String >classNameList=new ArrayList<>();
        try {

            DexFile df = new DexFile(context.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();

                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classNameList;
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
