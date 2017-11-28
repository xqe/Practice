package com.example.xieqe.test001.Util;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by xieqe on 2017/6/13.
 *
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance = new CrashHandler();
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context context;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handlerExceptionSelf(ex)) {
            defaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义处理
     * */
    private boolean handlerExceptionSelf(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);

        //再次检查，如果产生异常与当前异常相等，返回null，有不同的异常则继续写入
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        String result = writer.toString();

        // 省略部分：result再累加上日期、versionCode、versionName等版本信息，最后写入SD卡

        // 退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        return true;
    }
}
