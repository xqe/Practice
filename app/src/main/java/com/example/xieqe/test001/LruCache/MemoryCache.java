package com.example.xieqe.test001.LruCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2018/3/15.
 */

public class MemoryCache implements ICache {

    private static LruCache<String, Bitmap> memoryCache;

    private MemoryCache() {
        initLruCache();
    }

    private static class Holder {
        private static final MemoryCache INSTANCE = new MemoryCache();
    }

    public static MemoryCache getInstance() {
        return Holder.INSTANCE;
    }

    public static void initLruCache() {
        if (memoryCache != null) {
            return;
        }
        //初始化
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8; //缓存空间取内存的1/8
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //返回bitmap大小 单位：kb
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(String key) {
        return memoryCache.get(key);
    }

    @Override
    public void put(String key, Bitmap bitmap) {
        if (memoryCache.get(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    @Override
    public void clear() {

    }
}
