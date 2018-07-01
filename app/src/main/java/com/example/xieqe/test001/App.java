package com.example.xieqe.test001;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.example.xieqe.test001.annotation.InjectManager;
import com.example.xieqe.test001.annotation.InjectPage;

import java.util.List;

/**
 * Created by xqe on 2018/7/1.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        List<String> classNames = InjectManager.getClassName(this);
        Log.e("App========", "onCreate: " + classNames.size() );
        for (String className : classNames) {
            try {
                Class clazz = Class.forName(className);
                if (clazz.getAnnotation(InjectPage.class) != null) {
                    Log.e("App========", "find inject class: " + className);
                    //是否可以根据class修改字节码？
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
