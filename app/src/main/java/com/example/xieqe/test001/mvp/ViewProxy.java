package com.example.xieqe.test001.mvp;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieqe on 2018/2/28.
 */

public class ViewProxy<T extends IContract.IView> implements InvocationHandler {
    private static final String TAG = "AbstractViewCacheProxy";

    private final Map<Method,Object[]> viewCaches = new HashMap<>();//用来存储调用的方法和调用的参数
    private WeakReference<T> viewWeakReference; //真实的IView对象的弱引用

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isCacheMethod(method)) {
            viewCaches.put(method,args);
        }
        if (viewWeakReference != null && viewWeakReference.get() != null){
            return invokeMethod(viewWeakReference.get(),method,args);
        }
        return null;
    }

    private boolean isCacheMethod(Method method) {
        CacheMethod cacheMethod = method.getAnnotation(CacheMethod.class);
        return cacheMethod != null && cacheMethod.isCached();
    }

    private Object invokeMethod(Object view,Method method,Object[] args) {
        if (view != null && method != null) {
            try {
                return method.invoke(view,args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.i(TAG, "invokeMethod: " + e.toString());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    T proxy(Class<T> viewClass) {
        if (viewClass == null) {
            throw new NullPointerException("Proxy class is NULL");
        }
        return (T)Proxy.newProxyInstance(
                viewClass.getClassLoader(),new Class[]{viewClass},this);
    }

    void bind(T view) {
        if (view == null) {
            return;
        }
        unBind();
        viewWeakReference = new WeakReference<>(view);
        for (Method method : viewCaches.keySet()) {
            //再次bind()的时候则会回调缓存的方法
            invokeMethod(view,method,viewCaches.get(method));
            Log.i(TAG, "bind: " + method.getName());
        }
    }

    private void unBind() {
        if (viewWeakReference != null) {
            viewWeakReference.clear();
            viewWeakReference = null;
        }
    }

    void destroy() {
        unBind();
        viewCaches.clear();
    }
}
